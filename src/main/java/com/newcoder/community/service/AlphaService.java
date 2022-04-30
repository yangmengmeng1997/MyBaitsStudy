package com.newcoder.community.service;

import com.newcoder.community.dao.AlphaDao;
import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.User;
import com.newcoder.community.util.CommunityUtil;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @author xiuxiaoran
 * @date 2022/4/18 23:19
 */
@Service
//@Scope("prototype")    //复用型，不再使用单例 ，单例时single
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;

    @Autowired(required = false)
    private DiscussPostMapper discussPostMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

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

    //测试事务,内部发生错误直接回滚
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Object save1(){
        User user = new User();
        user.setUsername("test111");
        user.setSalt(CommunityUtil.generatedUUId().substring(0,5));
        user.setPassword(CommunityUtil.md5(123+user.getSalt()));
        user.setEmail("test111@qq.com");
        user.setHeaderUrl("http://iamge.newcoder.com/head/99t.png");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle("hello");
        post.setContent("aaaaaaaa");
        post.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(post);

        Integer.parseInt("aaa");
        return "ok";
    }

    //Spring 框架自带的事务管理，同理还是回滚了
    public Object save2(){
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                //在这里实现我们的逻辑
                User user = new User();
                user.setUsername("test222");
                user.setSalt(CommunityUtil.generatedUUId().substring(0,5));
                user.setPassword(CommunityUtil.md5(123+user.getSalt()));
                user.setEmail("tes222@qq.com");
                user.setHeaderUrl("http://iamge.newcoder.com/head/99t.png");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);

                DiscussPost post = new DiscussPost();
                post.setUserId(user.getId());
                post.setTitle("hello");
                post.setContent("aaaaaaaa");
                post.setCreateTime(new Date());
                discussPostMapper.insertDiscussPost(post);

                Integer.parseInt("aaa");

                return "ok";
            }
        });
    }
}
