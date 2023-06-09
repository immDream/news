package com.immdream.publishnews;

import org.mybatis.spring.annotation.MapperScan;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.immdream.publishnews")
@EnableCircuitBreaker       // 熔断器
@SpringBootApplication(scanBasePackages = {"com.immdream"})
@MapperScan(basePackages = {"com.immdream.publishnews.mapper"})
public class PublishNewsApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(PublishNewsApplication.class, args);
    }
}
