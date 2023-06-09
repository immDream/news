package com.immdream.publishnews.config;

import com.immdream.publishnews.quartz.ClearCache;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.publishnews.config
 *
 * @author immDream
 * @date 2023/06/08/22:52
 * @since 1.8
 */
@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail jobDetail() {
        //指定任务描述具体的实现类
        return JobBuilder.newJob(ClearCache.class)
                // 指定任务的名称
                .withIdentity("ClearCacheJob")
                // 任务描述
                .withDescription("任务描述：用户清除热点缓存")
                // 每次任务执行后进行存储
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger() {
        //创建触发器
        return TriggerBuilder.newTrigger()
                // 绑定工作任务
                .forJob(jobDetail())
                // 每隔 10*60 秒执行一次 job
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(60))
                .build();
    }
}