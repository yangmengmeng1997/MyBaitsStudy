package com.newcoder.community.config;

import com.newcoder.community.Quartz.AlphaJob;
import com.newcoder.community.Quartz.PostScoreRefreshJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * @author xiuxiaoran
 * @date 2022/5/7 19:27
 * 配置生成存储到数据库中再调用
 * FactoryBean 可简化Bean的实例化过程
 * FactoryBean 装配到容器中，再将Bean发送给其他的对象
 */
@Configuration
public class QuartzConfig {
//    @Bean
//    public JobDetailFactoryBean alphaJobDetail(){
//        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
//        factoryBean.setJobClass(AlphaJob.class);
//        factoryBean.setName("alphaJob");
//        factoryBean.setGroup("alphaJobGroup");
//        factoryBean.setDurability(true);  //存储
//        factoryBean.setRequestsRecovery(true);
//        return factoryBean;
//    }
//
//    @Bean
//    public SimpleTriggerFactoryBean alphaTrigger(JobDetail alphaJobDetail){  //参数和函数名字对应
//        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
//        factoryBean.setJobDetail(alphaJobDetail);
//        factoryBean.setName("alphaTrigger");
//        factoryBean.setGroup("alphaTriggerGroup");
//        factoryBean.setRepeatInterval(3000);
//        factoryBean.setJobDataMap(new JobDataMap());
//        return factoryBean;
//    }

    //为PostScoreRefreshJob配置,刷新帖子的分数
    @Bean
    public JobDetailFactoryBean PostScoreRefreshJobDetail(){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(PostScoreRefreshJob.class);
        factoryBean.setName("postScoreRefreshJob");
        factoryBean.setGroup("CommunityJobGroup");   //整个项目需要统一在这个组内
        factoryBean.setDurability(true);  //存储
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean PostScoreRefreshJobTrigger(JobDetail PostScoreRefreshJobDetail){  //参数和函数名字对应
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(PostScoreRefreshJobDetail);
        factoryBean.setName("postScoreRefreshJobTrigger");
        factoryBean.setGroup("CommunityTriggerGroup");  //整个项目需要统一在这个组内
        factoryBean.setRepeatInterval(1000*60*3);   //每隔3分钟执行一次
        factoryBean.setJobDataMap(new JobDataMap());
        return factoryBean;
    }
}
