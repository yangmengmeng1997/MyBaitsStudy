package com.newcoder.community.controller;

import com.newcoder.community.config.WKConfig;
import com.newcoder.community.entity.Event;
import com.newcoder.community.event.EventProducer;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.CommunityUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiuxiaoran
 * @date 2022/5/7 22:50
 * 分享生成图片的方法模拟
 */
@Controller
public class ShareController implements CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(WKConfig.class);

    //分享事件，异步处理
    @Autowired
    private EventProducer eventProducer;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

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
    //########################################

    //异步访问路劲
    //生成分享的逻辑在redis的相关的生产者和消费者里面
    @RequestMapping(value = "/share", method = RequestMethod.GET)
    @ResponseBody
    public String share(String htmlUrl){
        //生成文件名
        String fileName = CommunityUtil.generatedUUId();
        Event event = new Event()
                .setTopic(TOPIC_SHARE)
                .setData("htmlUrl",htmlUrl)
                .setData("fileName",fileName)
                .setData("suffix",".png");

        eventProducer.fireEvent(event);  //触发事件

        Map<String, Object> map = new HashMap<>();
        //map.put("shareUrl",domain+contextPath+"/share/image/"+fileName);
        map.put("shareUrl",shareBucketKeyUrl+"/"+fileName);  //路劲保存在云端服务器上面
        return CommunityUtil.getJSONString(0,null,map);
    }


    /*
       舍弃
     */
    //获取本地操作的图片舍弃，需要在云端存储显示
    //获取长图并且保存
    @RequestMapping(path = "/share/image/{fileName}",method = RequestMethod.GET)
    public void getShareImage(@PathVariable("fileName") String fileName, HttpServletResponse response){
        if(StringUtils.isBlank(fileName)){
            throw new IllegalArgumentException("文件名不为空");
        }
        response.setContentType("image/png");
        File file = new File(wkImageStorage+"\\"+fileName+".png");  //在本地文件夹读取文件并且返回
        try {
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int b=0;
            while ((b=fis.read(buffer))!=-1){
                os.write(buffer,0,b);
            } //读取图片
        } catch (IOException e) {
            logger.error("获取长图失败"+e);
            e.printStackTrace();
        }

    }

}
