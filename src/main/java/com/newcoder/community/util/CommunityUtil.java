package com.newcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import javax.lang.model.element.NestingKind;
import java.util.UUID;

/**
 * @author xiuxiaoran
 * @date 2022/4/22 15:01
 */
public class CommunityUtil {
    //生成上随机字符串
    public static String generatedUUId(){
        return UUID.randomUUID().toString().replaceAll("_","");
    }
    //生成MD5加密,但是不能解密+随机salt字符串来使得统一字符串生成的密码随机
    public static String md5(String key){
        if(StringUtils.isBlank(key)){  //字符串为空，不去处理
            return null ;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());  //哪里用到了salt了
    }
}
