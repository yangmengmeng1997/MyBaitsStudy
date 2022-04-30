package com.newcoder.community.dao;

import com.newcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xiuxiaoran
 * @date 2022/4/28 10:54
 */
@Mapper
public interface CommentMapper {
    //根据实体进行查询
    List<Comment> selectCommentByEntity(int entityType,int entityId,int offset,int limit);

    int selectRowsByEntity(int entityType,int entityId);

    //插入语句
    int insertComment(Comment comment);
}
