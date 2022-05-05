package com.newcoder.community.service;

import com.newcoder.community.CommunityApplication;
import com.newcoder.community.dao.LoginTicketMapper;
import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.LoginTicket;
import com.newcoder.community.entity.User;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.CommunityUtil;
import com.newcoder.community.util.MailClient;
import com.newcoder.community.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;

    //*****************************************登录相关*****************
//    @Autowired(required = false)
//    private LoginTicketMapper loginTicketMapper;
    //*****************************************************************

    public User findUserById(int id){
        //return userMapper.selectById(id);
        User user = getCache(id);  //从redis中查询
        if(user==null){
            user = initCache(id);  //从数据库中查再存到redis中
        }
        return user;
    }

    //但是这里你还是没有按照名字来进行优化，你只是优化了id。
    public User findUserByName(String name){
        return userMapper.selectByName(name);
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
    //激活之后用户的状态得到改变
    public int activation(int userId,String code){
        User user = userMapper.selectById(userId);
        if(user.getStatus()==1) return ACTIVATION_REPEAT;
        else if(user.getActivationCode().equals(code)){
            userMapper.updateStatus(userId,1);   //用户信息得到修改了
            clearCache(userId);    //将用户信息从缓存中删除，避免不一致的问题
            return ACTIVATION_SUCCESS;
        }else{
            return ACTIVATION_FAILURE;
        }
    }

    /*
       登录功能的实现 ，有多种情况，类似于注册，封装登录成功与否的消息
       参数： 用户名，用户密码，还有过期时间（秒）
     */
    public  Map<String,Object> login(String username, String password , int expired){
        Map<String,Object> loginInfo = new HashMap<>();
        //空值处理
        if (StringUtils.isBlank(username)) {
            loginInfo.put("usernameMsg","账号为空");
            return loginInfo;
        }
        if(StringUtils.isBlank(password)){
            loginInfo.put("passwordMsg","密码为空");
            return loginInfo;
        }
        //验证账号是否存在
        User user = userMapper.selectByName(username);
        if(user==null){
            loginInfo.put("usernameMsg","登录的账号不存在，请先注册");
            return loginInfo;
        }

        //账号没有激活也不能登录
        if(user.getStatus()==0){
            loginInfo.put("usernameMsg","该账号未激活，请先激活");
            return loginInfo;
        }


        //验证密码，注意数据库存储的面试加密过后的密码，所以我们也需要进行加密之后再比
        if(!user.getPassword().equals(CommunityUtil.md5(password)+user.getSalt())){
            loginInfo.put("passwordMsg","输入的密码错误!");
            return loginInfo;
        }


        if(!user.getPassword().equals(CommunityUtil.md5(password)+user.getSalt())){
            loginInfo.put("passwordMsg","输入的密码错误!");
            return loginInfo;
        }
        //账号密码都存在且正确,生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generatedUUId());  //生成随机字符串
        loginTicket.setStatus(0);  //0 表示登录有效
        loginTicket.setExpired(new Date(System.currentTimeMillis()+expired*1000));
        //将登录凭证存储在redis中
        String redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(redisKey,loginTicket);  //redis会将对象序列化为字符串？可是你哪个对象没有实现序列化接口

        //loginTicketMapper.insertLogin(loginTicket);  //存储登录凭证在数据库中
        loginInfo.put("ticket",loginTicket.getTicket());  // 存储ticket类似于这个session，之后将这个作为相应值发送给客户端
        return loginInfo;   //返回key-value
    }

    /*
       根据ticket，也就是登录凭证将状态设置为1即可
     */
    public void logout(String ticket){
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);   //获得登录凭证
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(redisKey,loginTicket);  //更新之前的对象
    }

    /*
       查询登录凭证
       根据登录凭证查询到登录用户的信息
     */
    public LoginTicket FindLoginTicket(String ticket){
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(redisKey);
    }

    /*
       修改图像路劲辅助完成换头像的功能
     */
    public int updateHeader(int userId,String headerUrl){
        int row = userMapper.updateHeader(userId,headerUrl);
        clearCache(userId);
        return row;
    }

    /*
       修改当前用户的密码,
       这边获取view的内容，转到controller处理
     */
    public int updatePassword(int userId,String password){

        int row = userMapper.updatePassword(userId,password);
        clearCache(userId);
        return row;
}


    /*
        用redis做用户缓存，先尝试在内存数据库中查找redis的用户信息，之后找不到再去mysql数据库中查找，经常登录的就比较快
        但是还有一点就是这个缓存信息需要有过期时间，不然和数据库没区别
        这里只演示了对ID进行优化处理，但是没有对名字进行优化处理
        1.优先从缓存中取值
        2.取不到初始化缓存数据
        3.数发生改变时需要删除redis缓存中的数据
     */
    // 1.优先从缓存中取值,查询不到连index都启动不了嘛。。。。
    private User getCache(int userId){
        String redisKey = RedisKeyUtil.getUserKey(userId);
        return (User) redisTemplate.opsForValue().get(redisKey);
    }

    //2.不到初始化缓存用户数据,将用户的数据存入redis中
    private User initCache(int userId){
        User user = userMapper.selectById(userId);
        String redisKey = RedisKeyUtil.getUserKey(userId);
        //注意这里缓存的是用户的登录过期时间，因为是临时的缓存
        redisTemplate.opsForValue().set(redisKey,user,3600, TimeUnit.SECONDS);
        return user;
    }

    //3.数据变更时清理缓存
    private void clearCache(int userId){
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);  //直接删除
    }

    //根据用户类型提供权限
//    public Collection<? extends GrantedAuthority> getAuthorities(int userId){
//        User user = userMapper.selectById(userId);
//
//        List<GrantedAuthority> list = new ArrayList<>();
//        list.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                switch (user.getType()){
//                    case 1:
//                        return AUTHORITY_ADMIN;
//                    case 2:
//                        return AUTHORITY_MODERATOR;
//                    default:
//                        return AUTHORITY_USER;
//                }
//            }
//        });
//        return list;
//    }
}
