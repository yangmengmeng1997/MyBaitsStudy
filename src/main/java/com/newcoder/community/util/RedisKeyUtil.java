package com.newcoder.community.util;

import javax.print.DocFlavor;

/**
 * @author xiuxiaoran
 * @date 2022/5/1 13:41
 * redis 的key值生成
 */
public class RedisKeyUtil {
    private static final String SPLIT = ":";

    private static final String PREFIX_ENTITY_LIKE = "like:entity";

    private static final String PREFIX_USER_LIKE="like:user";

    private static final String PREFIX_FOLLOWEE = "followee";   //关注的目标
    private static final String PREFIX_FOLLOWER = "follower";   //关注的粉丝

    private static final String PREFIX_KAPTCHA = "kaptcha";     //使用redis存储验证码ok

    private static final String PREFIX_TICKET = "ticket";

    private static final String PREFIX_USER = "user";

    private static final String PREFIX_UV = "uv";   //统计用户访问总数
    private static final String PREFIX_DAU = "dau";  //统计用户日活跃量

    private static final String PREFIX_POST = "post";  //帖子的算分相关

    //某个实体的key   like:entity:entityType:entityId   -> set<userId> 存储的是用户的ID
    public static String getEntityLikeKey(int entityType , int entityId){
        return PREFIX_ENTITY_LIKE+SPLIT+entityType+SPLIT+entityId;
    }

    //某个用户的赞 like:user:Id
    public static String getUserLikeKey(int userId){
        return PREFIX_USER_LIKE+SPLIT+userId;
    }

    //某个用户的关注实体，不一定是人啊，可以关注帖子等信息,userId 是当前这个用户的id，（表示谁在关注） ， entityType 关注的类型是什么可以是人，也可以是帖子
    // 关注的类型确定后 实体的ID来表示一下就可以定位到这个资源
    //格式为 key值为 followee:userId:entityType   存储的样式为：--> zset(entutyId,now)
    public static String getFolloweeKey(int userId,int entityType){
        return PREFIX_FOLLOWEE+SPLIT+userId+SPLIT+entityType;
    }

    //某个实体的粉丝量 ，可能是帖子或者其他，但是拥有的粉丝一定是确定的人，所以存储的是人的ID
    //格式为：key值为： follower+entityType:entityId   存储的样式为：-> zset(userId,now) 存储的是粉丝用户的ID和关注时间
    public static String getFollowerKey(int entityType,int entityId){
        return PREFIX_FOLLOWER+SPLIT+entityType+SPLIT+entityId;
    }

    //拼接验证码的key值,不同的用户的验证码是不一样的，需要识别出某个用户
    //给用户一个凭证使得临时标识用户
    public static String getKaptchaKey(String owner){
        return PREFIX_KAPTCHA+SPLIT+owner;
    }

    //返回登录凭证
    public static String getTicketKey(String ticket){
        return PREFIX_TICKET+SPLIT+ticket;
    }

    //返回用户的key
    public static String getUserKey(int userId){
        return PREFIX_USER+SPLIT+userId;
    }

    //返回单日的uv
    public static String getUVKey(String date){
        return PREFIX_UV + SPLIT + date;
    }

    //区间UV
    public static String getUVKey(String startTime,String endTime){
        return PREFIX_UV+SPLIT+startTime+SPLIT+endTime;
    }

    //单日活跃用户
    public static String getDAUKey(String date){
        return PREFIX_DAU+SPLIT+date;
    }
    //区间活跃用户
    public static String getDauKey(String startTime,String endTime){
        return PREFIX_DAU+SPLIT+startTime+SPLIT+endTime;
    }

    //返回帖子得分
    public static String getPostScoreKey(){
        return PREFIX_POST+SPLIT+"score";
    }
}
