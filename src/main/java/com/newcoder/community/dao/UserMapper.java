package com.newcoder.community.dao;

import com.newcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xiuxiaoran
 * @date 2022/4/20 10:25
 * 访问数据库user表的mapper接口
 */
@Mapper   //mybaits中的注解 对应于之前dao 接口的Repository
public interface UserMapper {
    User selectById(int id);
    User selectByName(String username);
    User selectByMail(String email);
    int insertUser(User user);
    int updateStatus(int id,int status);
    int updateHeader(int id,String headerUrl);
    int updatePassword(int id,String password);
}
