package com.newcoder.community.event;

import com.alibaba.fastjson.JSONObject;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.Event;
import com.newcoder.community.entity.Message;
import com.newcoder.community.service.DiscussPostService;
import com.newcoder.community.service.ElasticSearchService;
import com.newcoder.community.service.MessageService;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.CommunityUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

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

    @Value("${wk.image.command}")
    private String wkImageCommand;

    @Value("${wk.image.storage}")
    private String wkImageStorage;

    //#######################上传分享相关的设置
    @Value("${qiniu.key.access}")
    private String accessKey;

    @Value("${qiniu.key.secret}")
    private String secretKey;

    @Value("${qiniu.bucket.share.name}")
    private String shareBucketKeyName;

    @Value("${qiniu.bucket.share.url}")
    private String shareBucketKeyUrl;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;  //定时任务线程池
    //########################################

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

    //消费删除帖子的事件
    @KafkaListener(topics = {TOPIC_DELETE})
    public void handleDeleteMessage(ConsumerRecord record){
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
        elasticSearchService.deleteDiscussPost(event.getEntityId());  //将帖子从搜索服务器里面删除，表示搜索不到
    }

    //消费分享的事件
    /*
       修改为在云端存储访问
     */
    @KafkaListener(topics = TOPIC_SHARE)
    public void handleShareMessage(ConsumerRecord record){
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
        //处理事件， 得到对应的帖子id
        String HTMLUrl = (String) event.getData().get("htmlUrl");
        String fileName = (String) event.getData().get("fileName");
        String suffix = (String) event.getData().get("suffix");

        String cmd = wkImageCommand+" --quality 75 " + HTMLUrl + " " + wkImageStorage +"\\" + fileName + suffix;

        //执行cmd的命令
        try {
            logger.info(cmd);
            Runtime.getRuntime().exec(cmd);   //生成图片的过程比较长，但是主线程会继续向下走，如果图片还没有生成好，那么会上传失败
            logger.info("执行成功");
        } catch (IOException e) {
            logger.error("执行失败"+e);
            e.printStackTrace();
        }
        //启用定时器
        //在这里是生成长图成功
        //怎么保存到本地服务器？ ，就是使用定时器不断探查图片是否生成成功
        //只有某一个服务器会执行消费任务，所以使用上面的ThreadPoolTaskScheduler定时任务线程池，不需要考虑多台服务器的
        UploadTask task = new UploadTask(fileName,suffix);
        Future future = taskScheduler.scheduleAtFixedRate(task,500);
        task.setFuture(future);
    }
    //内部类封装
    //任务主体中考虑上传的逻辑
    //一般30s监视即可，失败就是失败了
    class UploadTask implements Runnable{
        //文件名称
        private String fileName;
        //文件后缀
        private String suffix;

        //启动任务·的返回值，用来停止监视线程
        private Future future;

        //开始时间
        private long startTime;
        //上传次数
        private int uploadTimes;

        public void setFuture(Future future) {
            this.future = future;
        }

        public UploadTask(String fileName, String suffix){
            this.fileName = fileName;
            this.suffix = suffix;
            this.startTime = System.currentTimeMillis();
        }
        @Override
        public void run() {
            //处理上传调用
            //失败情况
            if(System.currentTimeMillis()-startTime>30000){
                logger.error("上传超时"+fileName);
                future.cancel(true);  //停止任务
                return ;
            }
            //山传失败
            if(uploadTimes>3){
                logger.error("上传次数过多"+fileName);
                future.cancel(true);  //停止任务
                return ;
            }

            //开始执行上传图片，将本地图片上传到云端服务器
            String path = wkImageStorage + "/" + fileName + suffix;   //本地完整的图片名字
            File file = new File(path);
            if(file.exists()){
                logger.info(String.format("开始第%d次上传[%s]",++uploadTimes,fileName));
                //设置响应信息 ,和七牛云服务器相关
                StringMap policy = new StringMap();
                policy.put("returnBody", CommunityUtil.getJSONString(0));  //returnBody 固定的吗还是随便取？
                //生成上传凭证
                Auth auth = Auth.create(accessKey,secretKey);
                //生成上传凭证
                String uploadToken = auth.uploadToken(shareBucketKeyName,fileName,3600,policy);
                //指定上传的机房
                UploadManager manager = new UploadManager(new Configuration(Zone.zone2()));  //华南机房
                try{
                    //开始上传图片
                    Response response = manager.put(
                            path, fileName,uploadToken,null,"image/"+suffix,false
                    );
                    //处理响应的结果
                    JSONObject jsonObject = JSONObject.parseObject(response.bodyString());
                    if(jsonObject==null ||jsonObject.get("code")==null || !jsonObject.get("code").toString().equals("0")){
                        //发生异常
                        //System.out.println(jsonObject);
                        logger.info(String.format("第%d次上传失败,是253行出现异常",uploadTimes));
                    }else{
                        logger.info(String.format("第%d次上传成功",uploadTimes));
                        future.cancel(true);  //终止任务，上传成功
                    }
                }catch (QiniuException e){
                    logger.info(String.format("第%d次上传失败",uploadTimes)+e);
                }
            }else{
                logger.info("图片暂时没有生成");
            }
        }
    }
}
