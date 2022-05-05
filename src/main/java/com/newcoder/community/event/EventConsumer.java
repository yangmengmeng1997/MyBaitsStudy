package com.newcoder.community.event;

import com.alibaba.fastjson.JSONObject;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.Event;
import com.newcoder.community.entity.Message;
import com.newcoder.community.service.DiscussPostService;
import com.newcoder.community.service.ElasticSearchService;
import com.newcoder.community.service.MessageService;
import com.newcoder.community.util.CommunityConstant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiuxiaoran
 * @date 2022/5/2 19:31
 */
@Component
public class EventConsumer implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private MessageService messageService;  //需要操作message数据库

    @Autowired
    private DiscussPostService discussPostService;   //查询帖子

    @Autowired
    private ElasticSearchService elasticSearchService;  //查询出帖子

    @KafkaListener(topics = {TOPIC_COMMENT,TOPIC_LIKE,TOPIC_FOLLOW})
    public void handleMultiEvent(ConsumerRecord record){
        if(record==null || record.value()==null){
            logger.error("消息的内容为空");
            return;
        }
        //将接收的字符串消息转换为原来序列化的对象
        Event event = JSONObject.parseObject(record.value().toString(),Event.class);
        if(event==null){
            logger.error("消息格式错误");
            return;
        }
        //消息正确，准备发送站内通知
        Message message = new Message();
        message.setFromId(SYSTEM_ID);
        message.setToId(event.getEntityUserId());  //发送通知给的是被点赞或者被xxx的用户
        message.setConversationId(event.getTopic());  //存储操作主题
        message.setCreateTime(new Date());

        Map<String,Object> content = new HashMap<>();
        content.put("userId",event.getUserId());   //当前事件的触发者
        content.put("entityType",event.getEntityType());  //实体的类型，操作的是什么实体数库
        content.put("entityId",event.getEntityId());   //实体的ID

        if(!event.getData().isEmpty()){
            for(Map.Entry<String,Object> entry:event.getData().entrySet()){
                content.put(entry.getKey(),entry.getValue());
            }  //存储Event的额外Data数据都存入进来
        }
        message.setContent(JSONObject.toJSONString(content));
        messageService.addMessage(message);  //存储的是Message信息
    }

    //消费发帖子的事件
    @KafkaListener(topics = {TOPIC_PUBLISH})
    public void handlePublishMessage(ConsumerRecord record){
        if(record==null || record.value()==null){
            logger.error("消息的内容为空");
            return;
        }
        //将接收的字符串消息转换为原来序列化的对象
        Event event = JSONObject.parseObject(record.value().toString(),Event.class);
        if(event==null){
            logger.error("消息格式错误");
            return;
        }
        //处理事件， 得到对应的帖子id,然后查到对应的帖子存到es服务器中
        DiscussPost post = discussPostService.findDiscussPostById(event.getEntityId());
        elasticSearchService.saveDiscussPost(post);  //存储到es服务器中
    }
}
