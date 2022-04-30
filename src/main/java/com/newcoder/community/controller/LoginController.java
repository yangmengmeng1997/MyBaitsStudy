package com.newcoder.community.controller;

import com.google.code.kaptcha.Producer;
import com.newcoder.community.config.KapchaConfig;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CommunityConstant;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * @author xiuxiaoran
 * @date 2022/4/22 14:34
 */
@Controller
public class LoginController implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Producer KaptchProducer;  //生成的验证码的对象注入

    @Value("${server.servlet.context-path}")
    private String contextPath;

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


    /*
       敏感信息生成验证码存放在session里面
     */
    @RequestMapping(path = "/Kaptcha",method = RequestMethod.GET)
    public void getKaptch(HttpServletResponse response, HttpSession session){
        //生成验证码
        String text = KaptchProducer.createText();
        BufferedImage image = KaptchProducer.createImage(text);

        //生成验证码图片展示，储存验证码的session
        session.setAttribute("kaptcha",text);

        //将图片输出给浏览器
        response.setContentType("image/png");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);  //在浏览器输出图片
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("响应验证码失败");
        }
    }

    /*
       用户登录功能实现，先判断验证码对不对，验证码不对，直接失败，不需要再去查询数据库了
       这里用的post请求，区别于前面的get请求
       参数说明:
       需要传入表单中提交的 用户名字，密码，用户输入的验证码，页面勾选的标记，
       model需要装载返回的数据，session中存储之前生成的验证码，因此需要session。
       需要将登陆成功的数据ticket存入cookie中,需要使用HttpServletResponse对象来获取登录凭证
       session存储的是验证码，response携带的是自己存入的key-value作为cookie，
       session中的验证码参考getKaptch函数中 session.setAttribute("kaptcha",text);
     */
    @RequestMapping(path = "/login" ,method = RequestMethod.POST)
    public String login(String username,String password,String code,boolean remember,Model model,HttpSession session,HttpServletResponse response){
        String kaptcha = (String) session.getAttribute("kaptcha");
        //比较失败,返回登陆页面，验证码忽略大小写
        if(StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !StringUtils.equalsIgnoreCase(kaptcha,code)){
            model.addAttribute("codeMsg","验证码配对失败");
            return "/site/login";
        }

        //检查账号密码
        int expriedSeconds = remember ? REMEMBER_EXPIRED_SECONDS:DEFAULT_EXPIRED_SECONDS;  //超时时间根据remember与否设置
        Map<String, Object> loginInfo = userService.login(username, password, expriedSeconds);
        //登录成功 map里面就会存储 key-value的值 key是固定的"ticket" , value 是存储在登录表中的ticket登录凭证
        if(loginInfo.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket",loginInfo.get("ticket").toString());
            cookie.setPath(contextPath);  //设置作用范围的有效路劲
            cookie.setMaxAge(expriedSeconds);  //设置有效时间
            response.addCookie(cookie);  //服务器将cookie发送给客户端
            return "redirect:/index";   //重定向回首页
        }else{
            model.addAttribute("usernameMsg",loginInfo.get("usernameMsg"));
            model.addAttribute("passwordMsg",loginInfo.get("passwordMsg"));
            return "/site/login";   //有错误回到登录页面
        }
    }

    /*
       根据cookie来获取ticket判断
       参考AlphaController中的写法
     */
    @RequestMapping(path="/logout" ,method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String cookie){
        userService.logout(cookie);
        return "redirect:/login";  //重定向到login请求get,警告不用管，可以跳转即可
    }
}
