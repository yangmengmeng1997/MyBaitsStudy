package com.newcoder.community.controller;

import com.newcoder.community.entity.Comment;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.Event;
import com.newcoder.community.event.EventProducer;
import com.newcoder.community.service.CommentService;
import com.newcoder.community.service.DiscussPostService;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * @author xiuxiaoran
 * @date 2022/4/28 15:52
 */
@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;  // 获取当前用户

    @Autowired
    private EventProducer producer;

    @Autowired
    private DiscussPostService discussPostService;  //为了查到帖子的用户id

    @RequestMapping(path = "/add/{discussPostId}",method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment){
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);
        //添加评论后需要通知给用户了
        Event event = new Event()
                      .setTopic(TOPIC_COMMENT).setUserId(hostHolder.getUser().getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId())
                .setData("postId",discussPostId);  //辅助信息存储的是帖子ID，这个评论帖子通知需要连接到帖子页面
        //需要查询到是评论还是帖子的作者，分情况查看
        if (comment.getEntityType()==ENTITY_TYPE_POST){  //当前的是帖子
            DiscussPost target = discussPostService.findDiscussPostById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }else if(comment.getEntityType()==ENTITY_TYPE_COMMENT){  //当前的是评论
            Comment target = commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }
        producer.fireEvent(event);  //发送消费消息

        //添加评论时导致本帖子包含的内容发生变化了，也需要触发事件，只有对帖子评论时才可以触发
        //触发发帖子事件，将数据加入到es服务器上面，使得数据之后可以被搜索到
        if(comment.getEntityType()==ENTITY_TYPE_POST){
             event = new Event()
                    .setTopic(TOPIC_PUBLISH)
                    .setUserId(comment.getId())
                    .setEntityType(ENTITY_TYPE_POST)
                    .setEntityId(discussPostId);
             producer.fireEvent(event);  //再次触发事件
        }

        return "redirect:/discuss/detail/"+ discussPostId;  //重定向回原来的页面
    }
}
