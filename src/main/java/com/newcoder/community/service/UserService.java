package com.newcoder.community.service;

import com.newcoder.community.CommunityApplication;
import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.User;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.CommunityUtil;
import com.newcoder.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author xiuxiaoran
 * @date 2022/4/20 22:39
 */
@Service
public class UserService implements CommunityConstant {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public User findUserById(int id){
        return userMapper.selectById(id);
    }

    //用户注册功能，包含邮箱发送信息调用
    /*
       注册逻辑核心代码功能
       1.对异常值进行判断；
       2.对user里面的属性信息进行判断，，使用了common的工具包进行判断，将处理信息封装到Map中返回

     */
    public Map<String,Object> register(User user){
        Map<String,Object> map = new HashMap<>();
        //处理一些空值，极端值的判断
        if(user==null){
            throw new IllegalArgumentException("用户信息不能完全为空！");
        }
        //账号不能为空
        if(StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg","账号不能为空");
            return map;
        }
        //密码不能为空
        if(StringUtils.isBlank(user.getPassword())){
            map.put("userpasswordMsg","密码不能为空");
            return map;
        }
        //邮箱不能为空
        if(StringUtils.isBlank(user.getEmail())){
            map.put("useremailMsg","邮箱不能为空");
            return map;
        }

        //信息完全但是不一定正确，可能存在重复的情况
        //信息验证完毕，判断注册的信息是不是已经存在？
        //验证账号存在吗
        User u = userMapper.selectByName(user.getUsername());
        if(u!=null){
            map.put("usernameMsg","该账号已经存在");
            return map;
        }

        //验证邮箱
        u = userMapper.selectByMail(user.getEmail());
        if(u!=null){
            map.put("useremailMsg","该邮箱已存在");
            return map;
        }

        //判断完毕，是新的用户
        //注册用户
        user.setSalt(CommunityUtil.generatedUUId().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword())+user.getSalt()); //md5加密+salt加密
        user.setType(0);    //普通用户
        user.setStatus(0);  //没有激活
        user.setActivationCode(CommunityUtil.generatedUUId().substring(0,5));  //设置激活码，这个激活码在用到了？
        //设置随机头像，牛客网的提供的初始头像
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        //插入用户注册到数据库中
        userMapper.insertUser(user);

        //发送激活邮件 使用的Thymeleaf下面的Context类
        Context context = new Context();

        //生成需要使用的上下文变量
        //设置email
        context.setVariable("email",user.getEmail());

        //我们希望路劲是 http://localhost:8080/community/activation/101/code , 可以动态变化的
        //拼接发送的链接 code是激活码 101 是用户的id，注册时sql自增生成的。
        // 域名 + 项目名 + 功能名 + 用户名 + 用户的激活码
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();

        context.setVariable("url",url);

        //模板引擎生成路劲内容,将HTML序列化为String存储
        String content= templateEngine.process("/mail/activation",context);

        //发送邮件，发送方是163邮箱，接收方是用户的注册邮箱
        mailClient.sendMail(user.getEmail(),"激活账号", content);

        //测试一下url
//        System.out.println(context.getVariable("url"));
//        System.out.println(context.getVariable("email"));

        return map;   //返回map来包含发送的一些提示信息，map里面没有数据就表示没有问题
    }

    //返回激活的状态
    public int activation(int userId,String code){
        User user = userMapper.selectById(userId);
        if(user.getStatus()==1) return ACTIVATION_REPEAT;
        else if(user.getActivationCode().equals(code)){
            userMapper.updateStatus(userId,1);
            return ACTIVATION_SUCCESS;
        }else{
            return ACTIVATION_FAILURE;
        }
    }
}
