package com.newcoder.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiuxiaoran
 * @date 2022/5/7 19:52
 * 删除Job
 */
//@RunWith(SpringRunner.class)    //就是少加了这个注解，这个注解有啥用啊
//@SpringBootTest
//@ContextConfiguration(classes = CommunityApplication.class)
public class QuartzTests {
    @Autowired
    private Scheduler scheduler;   //不加会使得这个对象为空

    @Test
    public void testDeleteJob() {
        try {
            boolean result = scheduler.deleteJob(new JobKey("alphaJob", "alphaJobGroup"));
            System.out.println(result);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
