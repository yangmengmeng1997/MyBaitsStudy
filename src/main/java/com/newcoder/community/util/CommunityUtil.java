package com.newcoder.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import javax.lang.model.element.NestingKind;
import java.util.HashMap;
import java.util.Map;
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

    //Json相关的数据处理方法
    /*
       返回值：Json格式的字符串
       code: 编码
       msg:  提示信息
       map： 封装

     */
    public static String getJSONString(int code, String msg, Map<String,Object> map){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        if(map!=null){
            for(String key:map.keySet()){
                jsonObject.put(key,map.get(key));
            }
        }
        //JSON封装了之后的所有数据   ，返回Json数据处理的字符串
        return jsonObject.toJSONString();
    }

    public static String getJSONString(int code,String msg){
        return getJSONString(code,msg,null);
    }
    public static String getJSONString(int code){
        return getJSONString(code,null);
    }
    public static void main(String[] args){
        Map<String , Object> map = new HashMap<>();
        map.put("name","zhangsan");
        map.put("age",25);
        System.out.println(getJSONString(0));
    }
}
