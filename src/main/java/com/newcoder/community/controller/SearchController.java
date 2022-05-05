package com.newcoder.community.controller;

import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.Page;
import com.newcoder.community.service.ElasticSearchService;
import com.newcoder.community.service.LikeService;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CommunityConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiuxiaoran
 * @date 2022/5/4 22:28
 */
@Controller
public class SearchController implements CommunityConstant {
    @Autowired
    private ElasticSearchService elasticSearchService;

    @Autowired
    private UserService userService;  //查询关联信息时候需要显示

    @Autowired
    private LikeService likeService;

    Logger logger = LoggerFactory.getLogger(SearchController.class);

    //关键词在路劲上面传不太好吧,使用？号注入方式传参，需要前端传数据给你，名字和参数名字一样就好
    @RequestMapping(path = "/search",method = RequestMethod.GET)
    public String search(String keyWords, Page page, Model model){

        org.springframework.data.domain.Page<DiscussPost> searchResult = elasticSearchService.searchDiscussPost(keyWords,page.getCurrent()-1,page.getLimit());

        //聚合数据
        List<Map<String,Object>> discussPost = new ArrayList<>();
        if(searchResult!=null){
            for(DiscussPost post:searchResult){
                //遍历查询到的帖子，并且聚合额外的信息数据
                Map<String,Object> map = new HashMap<>();
                //帖子
                map.put("post",post);
                //发帖子的用户id
                map.put("user",userService.findUserById(post.getUserId()));
                //发帖子的点赞数量
                map.put("likeCount",likeService.findEntityLikeCount(ENTITY_TYPE_POST,post.getId()));
                //额外辅助信息补充完毕，添加到聚合信息到前端展示
                discussPost.add(map);
            }
        }
        model.addAttribute("discussPost",discussPost);
        model.addAttribute("keyWords",keyWords);

        //实现分页属性设置
        page.setPath("/search?keyWords="+keyWords);
        page.setRows(searchResult==null?0: (int) searchResult.getTotalElements());
        return "/site/search";
    }
}
