package com.newcoder.community;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newcoder.community.entity.Event;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.UserService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author xiuxiaoran
 * @date 2022/5/2 17:01
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class KafkaTest {

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    private UserService userService ;

    @Test
    public void test() throws InterruptedException {
        User user = userService.findUserById(168);
        kafkaProducer.send("test", "你好");
        Thread.sleep(1000*5);
    }
}

@Component
class KafkaProducer{
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(String topic,Object content){
        kafkaTemplate.send(topic,content);
    }
}

@Component
class KafkaConsumer{

    @KafkaListener(topics = {"test"})
    public void consume(ConsumerRecord record){
        System.out.println(record.value().toString());
    }
}