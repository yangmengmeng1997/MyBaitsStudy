package com.newcoder.community.service;

import com.newcoder.community.util.HostHolder;
import com.newcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @author xiuxiaoran
 * @date 2022/5/1 13:48
 * 点赞服务
 */
@Service
public class LikeService {
    @Autowired
    private RedisTemplate redisTemplate; //注入redis


    //点赞
    /*
       参数说明，
       userId：点赞的用户
       entityType：点赞的内容类型，是帖子还是回复
       entityId：点赞的标识ID
       EntityUserId : 发布帖子或者评论的userId
     */
    public void Like(int userId,int entityType,int entityId,int EntityUserId){
//        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
//        //判断当前用户是否点过赞，点过赞的id都会存储在redis中
//        Boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
//        if(isMember){  //如果已近点过赞了
//            redisTemplate.opsForSet().remove(entityLikeKey,userId);  //再点执行的就是删除操作
//        }else{
//            redisTemplate.opsForSet().add(entityLikeKey,userId);  //没有点赞过就添加这个userId
//        }
        //重构之前的方法
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
                String userLikeKey = RedisKeyUtil.getUserLikeKey(EntityUserId);
                //提前查询当前用户是否点过赞,事务执行过程中查询无效
                boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);
                //开启事务
                operations.multi();

                if(isMember){  //已近点过赞了
                    operations.opsForSet().remove(entityLikeKey,userId);  //再点执行的就是删除操作
                    operations.opsForValue().decrement(userLikeKey);     //并且将实体所属的用户的数量减1
                }else{
                    operations.opsForSet().add(entityLikeKey,userId);
                    operations.opsForValue().increment(userLikeKey);   //增加数量
                }

                return operations.exec();  // 返回事务执行完的结果
            }
        });
    }

    //查询某实体被点赞的数量
    public long findEntityLikeCount(int entityType,int entityId){
        //相同的实体会有固定的key，获取他并且进行查询
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);   //这个key有多少个id，就有多少个赞
    }

    //查询某用户是否点赞过 点赞，不点赞，踩，都是有可能的
    public int findEntityStatus(int userId,int entityType,int entityId){
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        //在集合里就是点过赞，返回1，不然返回0
        return redisTemplate.opsForSet().isMember(entityLikeKey,userId)?1:0;
    }

    //查询用户被点赞的数量
    public int findUserLikeCount(int EntityUserId){
        String userLikeKey = RedisKeyUtil.getUserLikeKey(EntityUserId);
        Integer integer = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return integer==null ? 0: integer;
    }
}
