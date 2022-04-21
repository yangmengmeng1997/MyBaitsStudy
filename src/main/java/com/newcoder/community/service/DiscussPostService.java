package com.newcoder.community.service;

import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiuxiaoran
 * @date 2022/4/20 22:30
 */

@Service
public class DiscussPostService {

    @Autowired(required = false)
    private DiscussPostMapper discussPostMapper;

    public List<DiscussPost> findDiscussPost(int userId, int offset , int limit){
        return discussPostMapper.selectDiscussPosts(userId,offset,limit);
    }

    public int findDiscussPostRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
