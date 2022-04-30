package com.newcoder.community.controller.interceptor;

import com.newcoder.community.annotation.LoginRequired;
import com.newcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author xiuxiaoran
 * @date 2022/4/26 16:11
 * 使用带有注解的拦截器
 * 这个目的是为了拦截一些页面，禁止没有登录就可以访问到必须要登录才可以访问的界面
 */
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
    @Autowired
    HostHolder hostHolder;  //需要获取当前用户
    @Override  //在请求开始之前判断是否合法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1、判断拦截的资源是不是方法，因为这里没有设置一些过滤条件啥的
        //如果拦截到的是方法的话，就是HandlerMethod这个类型
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //反射获取方法对象
            Method method = handlerMethod.getMethod();
            //尝试获取方法上面的特定标识，如果存在那么就是拦截对象
            LoginRequired annotation = method.getAnnotation(LoginRequired.class);
            if(annotation!=null && hostHolder.getUser()==null){  //当前方法有标识，并且用户没有登录 需要拦截
                response.sendRedirect(request.getContextPath() + "/login");  //response重定向，跳转到登录界面
                return false ; //不允许访问后续请求
            }
        }
        return true ;
    }
}
