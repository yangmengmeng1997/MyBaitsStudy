package com.newcoder.community.controller;

import com.newcoder.community.entity.Comment;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.Page;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.CommentService;
import com.newcoder.community.service.DiscussPostService;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.CommunityUtil;
import com.newcoder.community.util.HostHolder;
import org.apache.ibatis.ognl.Ognl;
import org.springframework.beans.factory.annotation.Autowired;
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
        //报错之后会统一处理
        return CommunityUtil.getJSONString(0,"发布成功");
    }

    //查询帖子详情数据的一些方法,增加参数添加Page用于分页
    //所有的实体类都会被分装到model中可以供前端使用数据
    @RequestMapping(value = "/detail/{discussPostId}" , method = RequestMethod.GET)
    public String selectPostById(@PathVariable("discussPostId") int id, Model model, Page page){ //故意声明的不一样也可以获取到数值吗,这个id是帖子的id不是user的id
        //帖子
        DiscussPost post = discussPostService.findDiscussPostById(id);
        model.addAttribute("post",post);
        //作者
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user",user);
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

    //增加帖子回复功能

}
