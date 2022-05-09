package com.newcoder.community.Quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @author xiuxiaoran
 * @date 2022/5/7 19:25
 */
@Component
public class AlphaJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(Thread.currentThread().getName()+": execute a quartz job.");
    }
}
