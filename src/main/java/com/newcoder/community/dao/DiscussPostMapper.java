package com.newcoder.community.dao;

import com.newcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiuxiaoran
 * @date 2022/4/20 21:33
 * 对应讨论帖数据的dao数据库操作
 */
@Mapper
public interface DiscussPostMapper {
    //userId传的是特定用户的ID，查询特定用户发的帖子, userId=0 时忽略，userId=其他时 查询特定用户
    // offset 表示当前页的起始行的行号 ， limit表示每一页的最多显示的数据
    List<DiscussPost> selectDiscussPosts(int userId , int offset , int limit);

    //参数起别名，动态SQL中如果只有一个参数会报错，所以必须起别名 , 统计评论总数
    int selectDiscussPostRows(@Param("userId") int userId);

    //插入数据
    int insertDiscussPost(DiscussPost discussPost);

    //显示数据
    DiscussPost selectDiscussPostById(int id);

    //更新评论的总数量
    int updateCommentCount(int id,int commentCount);
}
