package com.newcoder.community.controller.interceptor;

import com.newcoder.community.entity.LoginTicket;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CookieUtil;
import com.newcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author xiuxiaoran
 * @date 2022/4/25 19:03
 * 登录拦截器
 */
@Component
public class LoginTicketInterceptor implements HandlerInterceptor{
    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从浏览器中获取cookie
        String ticket = CookieUtil.getValue(request,"ticket");
        if(ticket!=null){  //表示登录了
            //根据ticket的值找到登录信息，通过完整的登录信息就可以找到user来传递信息了
            LoginTicket loginTicket = userService.FindLoginTicket(ticket);
            //判断凭证有没有失效
            //登录状态有没有效，并且保持登录的最长有效时长有没有结束
            if(loginTicket!=null && loginTicket.getStatus()==0 && loginTicket.getExpired().after(new Date())){
                //根据凭证查询到的用户id查找登录用户的信息准备之后显示
                User user = userService.findUserById(loginTicket.getUserId());

                //在本次请求中存储user,保证每一个线程都可以单独持有
                hostHolder.setUser(user);

                //构建用户认证的结果，并且存入SecurityContext中，便于认证授权
                //每次登录之后就存储用户的登录信息，并且保存用户的权限
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user, user.getPassword(),userService.getAuthorities(user.getId())
                );
                SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
            }
        }
        return true;   //只有为true才执行之后的
    }

    @Override   //在模板使用前在controller之后使用，需要存储起来让之后的模板进行使用
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if(user!=null && modelAndView!=null){
            modelAndView.addObject("loginUser",user);
        }
    }

    @Override   //模板使用完成之后需要清理掉所有的信息，结束回话
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
         hostHolder.clear();
         SecurityContextHolder.clearContext();  // 清理完用户之后也要清理用户的权限
    }
}
