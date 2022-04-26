package com.newcoder.community.util;

import com.newcoder.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author xiuxiaoran
 * @date 2022/4/25 19:37
 * 在多线程下保证用户信息的线程安全性
 * 代替session对象
 * ThreadLocal 叫做本地线程变量，
 * 意思是说，ThreadLocal 中填充的的是当前线程的变量，该变量对其他线程而言是封闭且隔离的，
 * ThreadLocal 为变量在每个线程中创建了一个副本，这样每个线程都可以访问自己内部的副本变量
 */
@Component
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<User>();

    //存储user
    public void setUser(User user){
        users.set(user);
    }

    //返回user
    public User getUser(){
        return users.get();
    }

    //清理数据
    public void clear(){
        users.remove();
    }
}
