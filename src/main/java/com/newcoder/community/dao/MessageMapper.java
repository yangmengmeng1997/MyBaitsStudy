package com.newcoder.community.dao;

import com.newcoder.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xiuxiaoran
 * @date 2022/4/28 19:09
 */
@Mapper
public interface MessageMapper {
    //1. 查询当前用户的总会话列表,每个会话只显示一条最新的数据显示即可,包括接受的和发送的会话
    List<Message> selectConversations(int userId,int offset,int limit);

    //2.查询当前用户的会话数量
    int selectConversationCount(int userId);

    //3.查询某个会话的私信列表
    List<Message> selectLetters(String conversationId,int offset,int limit);

    //4.查询某个会话的包含的私信数量
    int selectLetterCount(String conversationId);

    //5.查询未读私信的数量,这个查询的是某个用户某个会话的未读的数量，不加conversaionId的话就是一个用户所有的未读消息
    int selectLetterUnreadCount(int userId, String conversationId);

    //增加消息功能
    int insertMessage(Message message);

    //将未读消息改为已读 , 一次性修改多条消息
    int updateStatus(List<Integer> ids,int status);

    /*
       通知相关的消息显示
       三个主题；1.点赞，2.评论，3.跟随
       那么外面的通知列表依靠这三个方法基本可以实现
     */
    //1. 查询某个主题下面的最新通知即可
    Message selectLatestNotice(int userId,String topic);
    //2.查询某个主体包含的通知数量
    int selectNoticeCount(int userId,String topic);
    //3.显示未读的通知数量
    int selectNoticeUnreadCount(int userId,String topic);

    //查询某个主题所包含的通知列表
    List<Message> selectNotices(int userId,String topic ,int offset,int limit);
}
