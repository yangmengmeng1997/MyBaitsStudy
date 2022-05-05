package com.newcoder.community.controller.interceptor;

import com.newcoder.community.entity.User;
import com.newcoder.community.service.MessageService;
import com.newcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiuxiaoran
 * @date 2022/5/3 12:28
 * 拦截器显示未读消息数量，为了所有的请求都需要进过这个拦截器的处理
 */
@Component
public class MessageInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private MessageService messageService;

    @Override   //为什么是postHandle？在controller处理之后，在模板之前调用就可以
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if(user!=null && modelAndView!=null){
            int letterUnreadCount = messageService.findLetterUnreadCount(user.getId(),null);  //查询所有未读私信

            int noticeUnreadCount = messageService.findNoticeUnreadCount(user.getId(),null); //查询所有未读系统通知

            modelAndView.addObject("allUnreadCount",letterUnreadCount+noticeUnreadCount);
        }
    }
}
