package com.newcoder.community.event;

import com.alibaba.fastjson.JSONObject;
import com.newcoder.community.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author xiuxiaoran
 * @date 2022/5/2 19:27
 * 消息队列生产者
 * 在点赞，评论，跟随的时候都需要触发对应的controller来调用这个生产者方法来获取对象
 */
@Component
public class EventProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    //处理事件发送消息
    public void fireEvent(Event event){
        //将事件分布,事件主题是event里定义好的主题，内容是我们序列化好的对象序列字符串
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }
}
