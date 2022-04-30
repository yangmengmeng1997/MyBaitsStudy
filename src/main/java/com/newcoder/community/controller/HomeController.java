package com.newcoder.community.controller;

import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.Page;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.DiscussPostService;
import com.newcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiuxiaoran
 * @date 2022/4/20 22:48
 */
@Controller
public class HomeController {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    /*
        获得首页，并且分页的相关功能进行封装，进行响应
        model: 是需要经行封装的数据
        page ： 与分页有关的数据都封装好为一个函数
     */
    @RequestMapping(path = "/index" , method = RequestMethod.GET)
    public String getIndexPage(Model model , Page page){
        //查询总的记录数，0表示查询所有
        //SpringMVC 会自动实例化这个model和page，并且会将page注入model
        //所以可以直接在thymeleaf中直接访问Page的数据了？
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.findDiscussPost(0,page.getOffset(),page.getLimit());
        List<Map<String,Object>> discussPosts = new ArrayList<>();  //整合两个表的视图数据
        if(list!=null){
            for(DiscussPost post:list){
                Map<String,Object> map = new HashMap<>();
                map.put("post",post);   //存储的是讨论数据
                User user = userService.findUserById(post.getUserId());
                map.put("user",user);  //存储的是根据ID查询到的具体的User对象
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";
    }

    @RequestMapping(path = "/error",method = RequestMethod.GET)
    public String getErrorPage(){
        return "/error/500";
    }



}
