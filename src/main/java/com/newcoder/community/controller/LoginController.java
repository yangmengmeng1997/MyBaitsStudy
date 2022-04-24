package com.newcoder.community.controller;

import com.newcoder.community.entity.User;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author xiuxiaoran
 * @date 2022/4/22 14:34
 */
@Controller
public class LoginController implements CommunityConstant {

    @Autowired
    private UserService userService;

    @RequestMapping(path="/register" , method = RequestMethod.GET)
    public String getRegisterPage(){
        return "/site/register";
    }

    @RequestMapping(path="/login" , method = RequestMethod.GET)
    public String getLoginPage(){
        return "/site/login";
    }

    /*
       发送邮箱的注册功能 ，浏览器向我们服务器提交表单数据所以是post请求
     */
    @RequestMapping(path ="/register" , method = RequestMethod.POST)
    public String register(Model model, User user){
        //Spring矿框架会自动将信息注入到user对象的对应的属性中
        Map<String, Object> registerInfo = userService.register(user);
        if(registerInfo.isEmpty()){  //表示注册成功， 可以跳转到首页去，激活之后再去登录界面
            model.addAttribute("msg","注册成功，请去邮箱激活");
            model.addAttribute("target","/index");
            return "/site/operate-result";
        }
        //返回失败，跳转到注册页面，将三个报错信息都封装起来即可
        else{
            model.addAttribute("usernameMsg",registerInfo.get("usernameMsg"));
            model.addAttribute("userpasswordMsg",registerInfo.get("userpasswordMsg"));
            model.addAttribute("useremailMsg",registerInfo.get("useremailMsg"));
            return "/site/register";
        }
    }

    /*
        激活邮件返回的路劲
        我们希望路劲是 http://localhost:8080/community/activation/101/code , 可以动态变化的
     */
    @RequestMapping(path = "/activation/{userId}/{code}" , method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code){
        int result = userService.activation(userId,code);
        if(result==ACTIVATION_SUCCESS){
            model.addAttribute("msg","激活成功，现在可以正常使用了");  //返回一个信息
            model.addAttribute("target","/login");   //激活成功后需要跳转到其他的页面，这里是login页面
        }else if(result==ACTIVATION_REPEAT){
            model.addAttribute("msg","重复激活，该账号已经激活过了");  //返回一个信息
            model.addAttribute("target","/index");  //返回首页
        }else{
            model.addAttribute("msg","激活失败，激活码不正确");  //返回一个信息
            model.addAttribute("target","/index");  //返回首页
        }
        return "/site/operate-result";
    }
}
