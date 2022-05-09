package com.newcoder.community;

import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.service.DiscussPostService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author xiuxiaoran
 * @date 2022/5/8 22:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SpringBootTests {
    @Autowired
    private DiscussPostService discussPostService;

    private DiscussPost data;

    @BeforeClass  //类初始化之前执行，只执行一次，必须是静态的
    public static void beforeClass() {
        System.out.println("beforeClass");
    }

    @AfterClass  //类初始化之后执行，只执行一次，必须是静态的
    public static void afterClass() {
        System.out.println("afterClass");
    }

    @Before   //每次调用一个测试方法就会被调用，在测试方法之前被调用
    public void before() {
        System.out.println("before");

        // 初始化测试数据
        data = new DiscussPost();
        data.setUserId(111);
        data.setTitle("Test Title");
        data.setContent("Test Content");
        data.setCreateTime(new Date());
        discussPostService.addDiscussPost(data);
    }

    @After   //每次调用一个测试方法就会被调用， 在测试方法之后被调用
    public void after() {
        System.out.println("after");

        // 删除测试数据
        discussPostService.updateStatus(data.getId(), 2);
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test  //整体测试直接过，不是一个一个测，一般使用Assert做单元测试
    public void test2() {
        System.out.println("test2");
    }

    @Test
    public void testFindById() {
        DiscussPost post = discussPostService.findDiscussPostById(data.getId());
        Assert.assertNotNull(post);
        Assert.assertEquals(data.getTitle(), post.getTitle());
        Assert.assertEquals(data.getContent(), post.getContent());
    }

    @Test
    public void testUpdateScore() {
        int rows = discussPostService.updateScore(data.getId(), 2000.00);
        Assert.assertEquals(1, rows);

        DiscussPost post = discussPostService.findDiscussPostById(data.getId());
        Assert.assertEquals(2000.00, post.getScore(), 2);
    }
}
