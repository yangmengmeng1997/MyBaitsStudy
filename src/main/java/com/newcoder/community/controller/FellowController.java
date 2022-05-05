package com.newcoder.community.controller;

import com.newcoder.community.entity.Event;
import com.newcoder.community.entity.Page;
import com.newcoder.community.entity.User;
import com.newcoder.community.event.EventProducer;
import com.newcoder.community.service.FellowService;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.CommunityUtil;
import com.newcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiuxiaoran
 * @date 2022/5/1 20:32
 * 加关注和取关注两种操作
 */
@Controller
public class FellowController implements CommunityConstant {
    @Autowired
    private FellowService fellowService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private EventProducer producer;

    //异步操作，关注时需要发个系统通知，取关就不要通知了
    @RequestMapping(value = "/fellow",method = RequestMethod.POST)
    @ResponseBody
    public String follow(int entityType,int entityId){
        User user = hostHolder.getUser();
        //当前用户对某个实体进行关注操作 ， 必须要登录才能访问，这里需要使用拦截器，暂时没有实现
        fellowService.follow(user.getId(),entityType,entityId);

        //触发关注的通知
        Event event = new Event()
                .setTopic(TOPIC_FOLLOW).setUserId(hostHolder.getUser().getId())
                .setEntityType(entityType)
                .setEntityId(entityId)
                .setEntityUserId(entityId);
        //因为我们现在只能关注的是人，所以实体id和实体的userId是一致的,并且也不需要再连接到帖子页面
        //发送消息即可
        producer.fireEvent(event);

        return CommunityUtil.getJSONString(0,"已关注");
    }

    //某人取消对某个实体的关注操作
    @RequestMapping(value = "/unfellow",method = RequestMethod.POST)
    @ResponseBody
    public String unfollow(int entityType,int entityId){
        User user = hostHolder.getUser();
        //当前用户对某个实体进行关注操作 ， 必须要登录才能访问，这里需要使用拦截器，暂时没有实现
        fellowService.unfollow(user.getId(),entityType,entityId);
        return CommunityUtil.getJSONString(0,"已取消关注");
    }

    //找到当前用户的关注者列表
    @RequestMapping(path = "/followees/{userId}",method = RequestMethod.GET)
    public String getFollowees(@PathVariable("userId") int userId, Model model, Page page){
        User user = userService.findUserById(userId);
        if(user==null){
            throw new RuntimeException("该用户不存在");
        }
        model.addAttribute("user",user);
        page.setLimit(5);
        page.setPath("/followees/"+userId);
        page.setRows((int) fellowService.findFolloweeCount(userId,ENTITY_TYPE_USER));
        List<Map<String,Object>> userlist = fellowService.findFollowees(userId,page.getOffset(),page.getLimit());
        if(userlist!=null){
            for(Map<String,Object> map:userlist){
                User followeeUser = (User) map.get("user");  //取出目标user;
                map.put("hasFollowed",hasFollowed(followeeUser.getId()));  //存储关注状态用以显示，这个很奇怪，不是我关注列表不应该都是关注的状态嘛
            }
        }
        model.addAttribute("users",userlist);
        return "site/followee";
    }

    //查询某个用户的粉丝
    @RequestMapping(path = "/followers/{userId}",method = RequestMethod.GET)
    public String getFollowers(@PathVariable("userId") int userId, Model model, Page page){
        User user = userService.findUserById(userId);
        if(user==null){
            throw new RuntimeException("该用户不存在");
        }
        model.addAttribute("user",user);
        page.setLimit(5);
        page.setPath("/followers/"+userId);
        page.setRows((int) fellowService.findFollowerCount(ENTITY_TYPE_USER,userId));  //这里的userId是为了构建redis的key的，别混淆了
        List<Map<String,Object>> userlist = fellowService.findFollowers(userId,page.getOffset(),page.getLimit());
        if(userlist!=null){
            for(Map<String,Object> map:userlist){
                User followerUser = (User) map.get("user");  //取出目标user;
                map.put("hasFollowed",hasFollowed(followerUser.getId()));  //存储关注状态用以显示，这个很奇怪，不是我关注列表不应该都是关注的状态嘛
            }
        }
        model.addAttribute("users",userlist);
        return "site/follower";
    }


    private boolean hasFollowed(int userId){
        if(hostHolder.getUser()==null){
            return false;
        }// 没有登录就返回false
        return fellowService.isFollowed(hostHolder.getUser().getId(),ENTITY_TYPE_USER,userId);  //判断当前用户有没有关注过user
    }
}
