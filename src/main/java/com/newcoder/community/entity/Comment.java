package com.newcoder.community.entity;

import java.util.Date;

/**
 * @author xiuxiaoran
 * @date 2022/4/28 10:50
 */
public class Comment {
    private int id;
    private int userId;        //评论某个用户id发布的帖子
    private int entityType;   //评论的帖子类型，是发帖子评论还是回复某条帖子的内容的评论，这都可以设置
    private int entityId;     // 某条帖子的id ，就是你现在评论的帖子的id
    private int targetId;     // 这个表示评论是否针对某个特定的人回复，不是针对某个人就是0，是单独回复某个人就是某个人的user_id
    private String content;
    private int status;   //0 表示有效，其他的表示无效贴
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", entityType=" + entityType +
                ", entityId=" + entityId +
                ", targetId=" + targetId +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
