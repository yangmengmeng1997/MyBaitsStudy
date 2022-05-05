package com.newcoder.community.entity;


import org.apache.kafka.common.protocol.types.Field;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiuxiaoran
 * @date 2022/5/2 19:13zhaungmen
 * 专门用于封装事件的实体类
 * 这个类需要对系统发送的消息做一个封装，然后生产者和消费者都来处理这个事件
 */
public class Event {
    private String topic;   //消息主题
    private int userId;    //用户Id
    private int entityType;   //封装的事件类型，是点赞还是关注还是？
    private int entityId;    //事件的ID
    private int entityUserId;   //实体的作者是谁，需要记录
    private Map<String,Object> data = new HashMap<>();   //之后可能需要扩展的数据

    public String getTopic() {
        return topic;
    }

    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key,Object value) {
        this.data.put(key,value);
        return this;
    }

    @Override
    public String toString() {
        return "Event{" +
                "topic='" + topic + '\'' +
                ", userId=" + userId +
                ", entityType=" + entityType +
                ", entityId=" + entityId +
                ", entityUserId=" + entityUserId +
                ", data=" + data +
                '}';
    }
}
