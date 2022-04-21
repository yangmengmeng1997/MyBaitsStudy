package com.newcoder.community.service;

import com.newcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author xiuxiaoran
 * @date 2022/4/18 23:19
 */
@Service
//@Scope("prototype")    //复用型，不再使用单例 ，单例时single
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;

    public AlphaService(){
        System.out.println("Construct");
    }

    @PostConstruct
    public void init(){
        System.out.println("init....");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("destroy");
    }

    public String find(){
        return alphaDao.select();
    }
}
