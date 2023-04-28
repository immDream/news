package com.immdream.usermanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */
@EnableDiscoveryClient
@EnableCircuitBreaker       // 熔断器
@SpringBootApplication(scanBasePackages = {"com.immdream"})
@MapperScan(basePackages = {"com.immdream.usermanager.mapper"})
public class UserManagerApplication
{
    public static void main( String[] args )
    {
            SpringApplication.run(UserManagerApplication.class, args);

    }
}
