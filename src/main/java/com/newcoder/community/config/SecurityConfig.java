package com.newcoder.community.config;

import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.CommunityUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author xiuxiaoran
 * @date 2022/5/5 11:25
 * 权限配置类,先弃用掉
 */
//@Configuration
public class SecurityConfig implements CommunityConstant {
//    @Override
//    public void configure(WebSecurity web) {
//        //忽略掉对静态资源的拦截
//        web.ignoring().antMatchers("/resources/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//         //授权，需要指定相关的路劲拦截授权，自己看一遍
//         http.authorizeRequests()
//                 .antMatchers(
//                         "/user/setting",
//                         "/user/updatePassword",
//                         "/user/upload",
//                         "/discuss/addPost",
//                         "/comment/add/**",
//                         "/letter/**",
//                         "/notice/**",
//                         "/like",
//                         "/fellow",
//                         "/unfellow",
//                         "/followees/**",
//                         "/followers/**"
//                 )
//                 .hasAnyAuthority(AUTHORITY_ADMIN, AUTHORITY_USER, AUTHORITY_MODERATOR)
//                 .anyRequest().permitAll();
//         //权限不够时的处理，比如普通用户访问了超级管理员的权限等
//         http.exceptionHandling()
//                 .authenticationEntryPoint(new AuthenticationEntryPoint() {
//                     //没有登录做这样的处理
//                     @Override
//                     public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//                         String xRequest = request.getHeader("x-requested-with");
//                         if("XMLHttpRequest".equals(xRequest)){  //这是异步请求
//                             response.setContentType("application/plain;charset=utf-8");
//                             PrintWriter writer = response.getWriter();
//                             writer.write(CommunityUtil.getJSONString(403,"请先登录"));
//                         }else{
//                             response.sendRedirect(request.getContextPath()+"/login");
//                         }
//                     }
//                 })
//                 .accessDeniedHandler(new AccessDeniedHandler() {
//                     //权限不足
//                     @Override
//                     public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
//                         String xRequest = request.getHeader("x-requested-with");
//                         if("XMLHttpRequest".equals(xRequest)){  //这是异步请求
//                             response.setContentType("application/plain;charset=utf-8");
//                             PrintWriter writer = response.getWriter();
//                             writer.write(CommunityUtil.getJSONString(403,"你没有访问权限访问"));
//                         }else{
//                             response.sendRedirect(request.getContextPath()+"/denied");
//                         }
//                     }
//                 });
//
//         //Security底层默认拦截退出处理，需要进行配置做自己的退出请求
//         //覆盖其默认的拦截路劲不和我们的起冲突就好了
//         http.logout().logoutUrl("/securityLogout");  //设置不存在的路劲不执行退出拦截

    }
