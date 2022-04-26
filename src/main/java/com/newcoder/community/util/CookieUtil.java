package com.newcoder.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xiuxiaoran
 * @date 2022/4/25 19:08
 * 获取cookie
 * cookie 存储的是key-value的值 名称+值 例如： ticket-xxxxx
 */
public class CookieUtil {
    public static String getValue(HttpServletRequest request,String name){
        if(request==null || name==null){
            throw new IllegalArgumentException();
        }
        Cookie[] cookies = request.getCookies();   //获取所有的cookies
        if(cookies!=null){   //遍历找到特定的cookie
            for(Cookie cookie:cookies){
                if(cookie.getName().equals(name)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
