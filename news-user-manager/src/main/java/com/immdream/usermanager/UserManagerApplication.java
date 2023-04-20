package com.immdream.usermanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.immdream"})
@MapperScan(basePackages = {"com.immdream.usermanager.mapper"})
public class UserManagerApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(UserManagerApplication.class, args);
    }
}
