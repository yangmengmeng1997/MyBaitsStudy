package com.newcoder.community.service;

import com.newcoder.community.dao.MessageMapper;
import com.newcoder.community.entity.Message;
import com.newcoder.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author xiuxiaoran
 * @date 2022/4/28 21:15
 */
@Service
public class MessageService {
    @Autowired(required = false)
    private MessageMapper messageMapper;

    @Autowired
    SensitiveFilter sensitiveFilter;

    public List<Message> findConversations(int userId,int offset,int limit){
        return messageMapper.selectConversations(userId,offset,limit);
    }

    public int findConversationCount(int userId){
        return messageMapper.selectConversationCount(userId);
    }

    public List<Message> findLetters(String conversationId,int offset,int limit){
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    public int findLetterCount(String conversationId){
        return messageMapper.selectLetterCount(conversationId);
    }

    public int findLetterUnreadCount(int userId, String conversationId){
        return messageMapper.selectLetterUnreadCount(userId,conversationId);
    }

    //添加消息
    public int addMessage(Message message){
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveFilter.filter(message.getContent()));
        return messageMapper.insertMessage(message);
    }

    //将未读的消息变为已读
    public int readMessage(List<Integer> ids){
        return messageMapper.updateStatus(ids,1); //0表示未读，1表示已读
    }
}
