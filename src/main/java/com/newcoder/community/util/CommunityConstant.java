package com.newcoder.community.util;

/**
 * @author xiuxiaoran
 * @date 2022/4/22 19:36
 * 设置激活的集中状态
 *     成功： 0
 *     重复激活 ：1
 *     激活失败 ：2
 */
public interface CommunityConstant {
    int ACTIVATION_SUCCESS = 0;

    int ACTIVATION_REPEAT = 1;

    int ACTIVATION_FAILURE = 2;

    /*
      默认登录的过期时间，较短,默认存12个小时
     */
    int DEFAULT_EXPIRED_SECONDS = 3600*12 ;

    /*
      勾选记住我的过期时间，较长,默认存储24个小时 太长的话时间会溢出，就像之前的3600*24*30 不行，直接溢出登录失败
     */
    int REMEMBER_EXPIRED_SECONDS = 3600*24;

    /*
      实体类型：帖子  --1
      实体类型：评论  --2
      用户实体      --3
     */
    int ENTITY_TYPE_POST = 1;
    int ENTITY_TYPE_COMMENT = 2;
    int ENTITY_TYPE_USER = 3;

    /*
       消息通知的相关常量
       消息主题定义
       评论："comment"
       点赞："like"
       关注："follow"
       发帖：“publish"
       删帖：“delete”
       事件的主体均需要在kafka里面定义
     */
     String TOPIC_COMMENT = "comment";
     String TOPIC_LIKE = "like";
     String TOPIC_FOLLOW = "follow";

     String TOPIC_PUBLISH = "publish";
     String TOPIC_DELETE = "delete";


     /*
        系统用户的Id=1
      */
     int SYSTEM_ID=1;

     /*
        权限控制
        普通用户：user
        管理员：admin
        版主：moderator
      */
     String AUTHORITY_USER = "user";
     String AUTHORITY_ADMIN = "admin";
     String AUTHORITY_MODERATOR = "moderator";

     /*
        分享主题名
      */
     String TOPIC_SHARE = "share";
 }
