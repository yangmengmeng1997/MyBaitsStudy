package com.newcoder.community.util;

/**
 * @author xiuxiaoran
 * @date 2022/4/22 19:36
 * 设置激活的集中状态
 *     成功： 0
 *     重复激活 ：1
 *     激活失败 ：2
 */
public interface CommunityConstant {
    int ACTIVATION_SUCCESS = 0;

    int ACTIVATION_REPEAT = 1;

    int ACTIVATION_FAILURE = 2;

    /*
      默认登录的过期时间，较短,默认存12个小时
     */
    int DEFAULT_EXPIRED_SECONDS = 3600*12 ;

    /*
      勾选记住我的过期时间，较长
     */
    int REMEMBER_EXPIRED_SECONDS = 3600*24*30;
 }
