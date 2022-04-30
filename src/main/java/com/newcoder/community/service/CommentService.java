package com.newcoder.community.service;

import com.newcoder.community.dao.CommentMapper;
import com.newcoder.community.entity.Comment;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @author xiuxiaoran
 * @date 2022/4/28 11:13
 */
@Service
public class CommentService implements CommunityConstant {
    @Autowired(required = false)
    private CommentMapper commentMapper;

    @Autowired
    SensitiveFilter sensitiveFilter;

    @Autowired
    private DiscussPostService discussPostService;

    public List<Comment> findCommentByEntity(int entityType,int entityId,int offset,int limit){
        return commentMapper.selectCommentByEntity(entityType,entityId,offset,limit);
    }

    public int findRowsByEntity(int entityType,int entityId){
        return commentMapper.selectRowsByEntity(entityType,entityId);
    }

    //增加帖子的业务,操作时保证原子性,还需要敏感词过滤
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public int addComment(Comment comment){
        if(comment==null){
            throw new IllegalArgumentException("参数不能为空");
        }
        //添加评论
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        int rows = commentMapper.insertComment(comment);

        //更新帖子的评论数量
        if(comment.getEntityType()==ENTITY_TYPE_POST){
            int nums = commentMapper.selectRowsByEntity(ENTITY_TYPE_POST,comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(),nums);
        }
        return rows;
    }
}
