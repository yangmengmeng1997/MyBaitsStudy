package com.newcoder.community.controller;

import com.newcoder.community.entity.Event;
import com.newcoder.community.entity.User;
import com.newcoder.community.event.EventProducer;
import com.newcoder.community.service.LikeService;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.CommunityUtil;
import com.newcoder.community.util.HostHolder;
import com.newcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiuxiaoran
 * @date 2022/5/1 14:05
 * 点赞表示层
 */
@Controller
public class LikeController implements CommunityConstant {
    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer producer;

    @Autowired
    private RedisTemplate redisTemplate;

    //点赞传入一些信息进来，所以是post请求，异步请求，不转发刷新页面的
    //这里需要通过拦截器组织直接访问该页面的功能，你还记得吗？自己实现？，只有会使用SpringSecurite
    //一种是对帖子点赞，一种是对评论进行点赞
    //多加入一个参数是点赞的帖子id，方便之后的链接回去
    @RequestMapping(path="/like",method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType,int entityId,int entityUserId,int postId){
        User user = hostHolder.getUser();

        //点赞
        likeService.Like(user.getId(),entityType,entityId,entityUserId);


        //统计点赞的状态和点赞数量，好动态更新
        //数量
        long likeCount = likeService.findEntityLikeCount(entityType,entityId);

        //状态
        int likeStatus = likeService.findEntityStatus(user.getId(),entityType,entityId);

        //封装消息后传入
        Map<String,Object> map = new HashMap<>();

        map.put("likeCount",likeCount);
        map.put("likeStatus",likeStatus);



        //点赞功能结束之后添加消息发送,点赞才触发这个消息发送
        if(likeStatus==1){
            Event event = new Event()
                    .setTopic(TOPIC_LIKE).setUserId(hostHolder.getUser().getId())
                    .setEntityType(entityType)
                    .setEntityId(entityId)
                    .setEntityUserId(entityUserId)
                    .setData("postId",postId);
            //构造好消息实体
            producer.fireEvent(event);  //系统发送消息通知
        }

        //判断对帖子点赞就加分
        if(entityType==ENTITY_TYPE_POST){
            //标记需要计算分数的帖子ID
            String redisKey = RedisKeyUtil.getPostScoreKey();
            //重复的情况，但是又只需要算一次，去重，只是记录哪些帖子需要进行存储而已,以下同理
            redisTemplate.opsForSet().add(redisKey,postId);
        }

        //装换为json字符串给页面
        return CommunityUtil.getJSONString(0,null,map);
    }
}
