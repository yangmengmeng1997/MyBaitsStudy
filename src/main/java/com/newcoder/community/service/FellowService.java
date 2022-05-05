package com.newcoder.community.service;

import com.newcoder.community.entity.User;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xiuxiaoran
 * @date 2022/5/1 20:17
 */
@Service
public class FellowService implements CommunityConstant {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    //关注
    /*
       userId，表示谁在关注
       entityType ： 关注的是谁，发的帖子还是用户
       entityId： 关注对象的id
     */
    public void follow(int userId,int entityType,int entityId){
        //使用事务
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);
                String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);

                operations.multi();
                //存储原子操作
                operations.opsForZSet().add(followeeKey,entityId,System.currentTimeMillis());
                operations.opsForZSet().add(followerKey,userId,System.currentTimeMillis());

                return operations.exec();
            }
        });
    }

    //取关
    public void unfollow(int userId,int entityType,int entityId){
        //使用事务
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);
                String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);

                operations.multi();
                //存储原子操作
                operations.opsForZSet().remove(followeeKey,entityId);
                operations.opsForZSet().remove(followerKey,userId);

                return operations.exec();
            }
        });
    }

    //查询某个用户关注的某一类实体的数量
    public long findFolloweeCount(int userId,int entityType){
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);
        return redisTemplate.opsForZSet().zCard(followeeKey);  //统计关注的实体的数量
    }

    //查询了某个实体的粉丝数量，可以是用户，可以是帖子的关注度
    public long findFollowerCount(int entityType,int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        return redisTemplate.opsForZSet().zCard(followerKey);  //统计数量
    }

    //查询当前用户的是否follow某个实体
    public boolean isFollowed(int userId,int entityType,int entityId){
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);
        return redisTemplate.opsForZSet().score(followeeKey,entityId)!=null;  //统计存在与否
    }


    //查询某个用户关注的人，并且将数据进行展示
    public List<Map<String,Object>> findFollowees(int userId,int offset,int limit){
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,ENTITY_TYPE_USER);  //因为明确是查询人，所以类型就写死了
        //存储的是关注的人的用户id，我们根据id进行展示即可。
        Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(followeeKey, offset, offset + limit - 1);
        if(targetIds==null){
            return null;
        }
        List<Map<String,Object>> list = new ArrayList<>();
        for(Integer targetId:targetIds){  //遍历id查询存储
            Map<String,Object> map = new HashMap<>();
            User user = userService.findUserById(targetId);
            map.put("user",user);  //存储关注的用户信息
            Double score = redisTemplate.opsForZSet().score(followeeKey,targetId);
            map.put("followTime",new Date(score.longValue()));  //存储关注用户的Id，存储关注的时间
            list.add(map);
        }
        return list;
    }

    //查询某用户被关注的列表，也就是粉丝列表、
    //这里的id是userId ，可以返回看followerKey 结构定义，key是由当前用户的id构成，存储的值是其他用户的id值，也就是关注当前用户的id
    public List<Map<String,Object>> findFollowers(int userId,int offset,int limit){
        String followerKey = RedisKeyUtil.getFollowerKey(ENTITY_TYPE_USER,userId);  //因为明确是查询人，所以类型就写死了,为什么里的id是userid
        //存储的是关注的人的用户id，我们根据id进行展示即可。
        //redis 内部做了处理，返回的是有序的集合
        Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(followerKey, offset, offset + limit - 1);
        if(targetIds==null){
            return null;
        }
        List<Map<String,Object>> list = new ArrayList<>();
        for(Integer targetId:targetIds){  //遍历id查询存储
            Map<String,Object> map = new HashMap<>();
            User user = userService.findUserById(targetId);
            map.put("user",user);
            Double score = redisTemplate.opsForZSet().score(followerKey,targetId);
            map.put("followTime",new Date(score.longValue()));  //存储关注用户的Id，存储关注的时间
            list.add(map);
        }
        return list;
    }

}
