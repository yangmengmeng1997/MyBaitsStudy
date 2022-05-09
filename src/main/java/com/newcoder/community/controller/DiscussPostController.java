package com.newcoder.community.controller;

import com.newcoder.community.entity.*;
import com.newcoder.community.event.EventProducer;
import com.newcoder.community.service.*;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.CommunityUtil;
import com.newcoder.community.util.HostHolder;
import com.newcoder.community.util.RedisKeyUtil;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author xiuxiaoran
 * @date 2022/4/27 21:28
 * 帖子详情页面是在这里处理的，需要在这里进行修改
 * 查询帖子下面的详细评论也需要在这里注入进来
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements CommunityConstant {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    private UserService userService;  //需要联合user表查询用户的信息

    @Autowired
    private CommentService commentService;  //需要查询评论详情

    @Autowired
    private LikeService likeService;  //帖子点赞的一些处理

    @Autowired
    private ElasticSearchService elasticSearchService;   //增加es搜索功能

    @Autowired
    private EventProducer producer;  //触发消息队列生产者

    @Autowired
    private RedisTemplate redisTemplate;  // 向redis中存储相关的数据，计算得分
    /*
       影响帖子得分的行为都加了标记处理，一共有四个：
       新增，加精，评论，点赞
       利用Quartz做定时处理即可
     */

    @RequestMapping(path="/addPost",method = RequestMethod.POST)
    @ResponseBody
    public String addPost(String title,String content){
        User user = hostHolder.getUser();
        if(user==null){ //403表示状态码为没有权限
            return CommunityUtil.getJSONString(403,"未登录不能发布帖子");
        }
        //如果已近登录了
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);

        //触发发帖子事件，将数据加入到es服务器上面，使得数据之后可以被搜索到
        Event event = new Event()
                      .setTopic(TOPIC_PUBLISH)
                .setUserId(user.getId())
                .setEntityType(ENTITY_TYPE_POST)
                .setEntityId(post.getId());

        producer.fireEvent(event);  //触发事件

        //标记需要计算分数的帖子ID
        String redisKey = RedisKeyUtil.getPostScoreKey();
        //重复的情况，但是又只需要算一次，去重，只是记录哪些帖子需要进行存储而已,以下同理
        redisTemplate.opsForSet().add(redisKey,post.getId());

        //报错之后会统一处理
        return CommunityUtil.getJSONString(0,"发布成功");
    }

    //查询帖子详情数据的一些方法,增加参数添加Page用于分页
    //所有的实体类都会被分装到model中可以供前端使用数据
    @RequestMapping(value = "/detail/{discussPostId}" , method = RequestMethod.GET)
    public String selectPostById(@PathVariable("discussPostId") int id, Model model, Page page){
        //故意声明的不一样也可以获取到数值吗,这个id是帖子的id不是user的id
        //帖子
        DiscussPost post = discussPostService.findDiscussPostById(id);
        model.addAttribute("post",post);
        //作者
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user",user);

        //帖子的点赞数量等
        long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST,id);  //查询帖子的数量点赞的ID
        model.addAttribute("likeCount",likeCount);

        //点赞状态 没有登录就没法显示对应的用户看见的状态,又是写反了吗。。。。为null值就是0
        int likeStatus =hostHolder.getUser()==null?0:likeService.findEntityStatus(hostHolder.getUser().getId(),ENTITY_TYPE_POST,id);
        model.addAttribute("likeStatus",likeStatus);

        //还有帖子的回复等信息都没有查询，先不急
        //完善
        page.setLimit(5);
        page.setPath("/discuss/detail/"+id);
        page.setRows(post.getCommentCount());

        /*
            存在一种父子集的关系
            评论：指的是给帖子发的评论信息   -- 评论列表
            回复：指的是给帖子下面的评论回复的信息，叫做回复   -- 回复列表
         */

        //评论列表，传入的是帖子的id
        List<Comment> commentList = commentService.findCommentByEntity(ENTITY_TYPE_POST,post.getId(),page.getOffset(),page.getLimit());
        //将里面的一些userid等显示为头像和用户信息 ，评论显示列表
        List<Map<String,Object>> commentVoList = new ArrayList<>();
        if(commentList!=null){
            for(Comment comment:commentList){  //对于每一条评论
                Map<String,Object> commentvo = new HashMap<>();   //构建评论对应的显示
                //存储评论
                commentvo.put("comment",comment);
                //存储评论关联的用户信息
                commentvo.put("user",userService.findUserById(comment.getUserId()));
                //帖子有评论，评论下面又有多条评论

                //加入点赞的功能补充
                //帖子的点赞数量等
                long likeCountComment = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT,comment.getId());  //查询帖子的数量点赞的ID
                commentvo.put("likeCountComment",likeCountComment);

                //点赞状态 没有登录就没法显示对应的用户看见的状态
                int likeStatusComment =hostHolder.getUser()==null?0:likeService.findEntityStatus(hostHolder.getUser().getId(),ENTITY_TYPE_COMMENT,comment.getId());
                commentvo.put("likeStatusComment",likeStatusComment);
                //
                //回复列表，选取的是某条评论下面的所有回复评论消息,不做分页处理
                //传入的是这条评论的Id
                List<Comment> replyList = commentService.findCommentByEntity(ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE);
                //存储回复vo视图
                List<Map<String,Object>> replyVoList = new ArrayList<>();
                //每一条回复我们也要展示相关联的用户信息
                for(Comment reply:replyList){
                    Map<String, Object> replyvo = new HashMap<>();
                    //存储回复
                    replyvo.put("reply",reply);
                    //存储回复的user信息
                    replyvo.put("user",userService.findUserById(reply.getUserId()));

                    //增加点赞状态
                    //帖子的点赞数量等
                    long likeCountReply = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT,reply.getId());  //查询帖子的数量点赞的ID
                    replyvo.put("likeCountReply",likeCountReply);

                    //点赞状态 没有登录就没法显示对应的用户看见的状态
                    int likeStatusReply =hostHolder.getUser()==null?0:likeService.findEntityStatus(hostHolder.getUser().getId(),ENTITY_TYPE_COMMENT,reply.getId());
                    replyvo.put("likeStatusReply",likeStatusReply);

                    //回复的目标也就是target-id 是多少
                    User target = reply.getTargetId()==0?null:userService.findUserById(reply.getTargetId());
                    replyvo.put("target",target);
                    replyVoList.add(replyvo);  // 添加封装好的回复消息
                }

                //将每一条对应评论对应的回复数量都需要加在对应的哦评论里
                commentvo.put("replys",replyVoList);
                //包含的回复数量
                int replyCount = commentService.findRowsByEntity(ENTITY_TYPE_COMMENT, comment.getId());
                commentvo.put("replyCount",replyCount);
                //封装好评论整体数据
                commentVoList.add(commentvo);
            }
        }
        model.addAttribute("comments",commentVoList);  //得到的是一个list集合，每一个元素都是一个map
        return "/site/discuss-detail";
    }

    //--------------------------------------------------------------------------------------------------
    //增加更新帖子逻辑,修改需要提交数据
    //异步请求不刷新
    //置顶
    @RequestMapping(path = "/top" , method = RequestMethod.POST)
    @ResponseBody
    public String setTop(int id){
        discussPostService.updateType(id,1);

        //触发发帖子事件，将数据加入到es服务器上面，使得数据之后可以被搜索到
        Event event = new Event()
                .setTopic(TOPIC_PUBLISH)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(ENTITY_TYPE_POST)
                .setEntityId(id);
        producer.fireEvent(event);  //触发事件

        return CommunityUtil.getJSONString(0);
    }

    //加精
    @RequestMapping(path = "/wonderful" , method = RequestMethod.POST)
    @ResponseBody
    public String setWonderful(int id){
        discussPostService.updateStatus(id,1);

        //触发发帖子事件，将数据加入到es服务器上面，使得数据之后可以被搜索到
        Event event = new Event()
                .setTopic(TOPIC_PUBLISH)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(ENTITY_TYPE_POST)
                .setEntityId(id);
        producer.fireEvent(event);  //触发事件

        //标记需要计算分数的帖子ID，置顶不需要标记
        String redisKey = RedisKeyUtil.getPostScoreKey();
        //重复的情况，但是又只需要算一次，去重，只是记录哪些帖子需要进行存储而已,以下同理
        redisTemplate.opsForSet().add(redisKey,id);

        return CommunityUtil.getJSONString(0);
    }

    //删除
    @RequestMapping(path = "/delete" , method = RequestMethod.POST)
    @ResponseBody
    public String setDelete(int id){
        discussPostService.updateStatus(id,2);  //帖子为2表示删除

        //触发删除帖子事件，将数据加入到es服务器上面，使得数据之后可以被搜索到
        Event event = new Event()
                .setTopic(TOPIC_DELETE)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(ENTITY_TYPE_POST)
                .setEntityId(id);
        producer.fireEvent(event);  //触发事件
        return CommunityUtil.getJSONString(0);
    }


}
