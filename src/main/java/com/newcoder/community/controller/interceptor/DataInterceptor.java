package com.newcoder.community.controller.interceptor;

import com.newcoder.community.entity.User;
import com.newcoder.community.service.DataService;
import com.newcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiuxiaoran
 * @date 2022/5/7 15:46
 * 拦截器前后执行某些功能
 */
@Component
public class DataInterceptor implements HandlerInterceptor {
    @Autowired
    private DataService dataService;

    @Autowired
    HostHolder hostHolder;

    //统计数据
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //统计UV
        String ip = request.getRemoteHost();
        dataService.recordUV(ip);
        //统计DAU
        User user = hostHolder.getUser();
        //判断用户有没有登录
        if(user!=null){  //user登录才统计
            dataService.recordDAU(user.getId());
        }
        return true;
    }
}
