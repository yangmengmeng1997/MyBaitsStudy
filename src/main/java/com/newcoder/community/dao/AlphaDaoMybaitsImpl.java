package com.newcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * @author xiuxiaoran
 * @date 2022/4/18 23:11
 */
@Repository
@Primary //有多个实现类时优先实现这个类
public class AlphaDaoMybaitsImpl implements AlphaDao {

    @Override
    public String select() {
        return "MybaitsImpl";
    }
}
