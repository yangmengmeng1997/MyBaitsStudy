package com.newcoder.community.service;

import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiuxiaoran
 * @date 2022/4/20 22:39
 */
@Service
public class UserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    public User findUserById(int id){
        return userMapper.selectById(id);
    }
}
