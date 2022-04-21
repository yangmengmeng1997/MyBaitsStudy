package com.newcoder.community.dao;

import org.springframework.stereotype.Repository;

/**
 * @author xiuxiaoran
 * @date 2022/4/18 22:58
 */
@Repository("alphaDaoImpl")   //访问数据库的特殊注解,并且设置访问bin的名字
public class AlphaDaoImpl implements AlphaDao {
    @Override
    public String select() {
        return "select....";
    }
}
