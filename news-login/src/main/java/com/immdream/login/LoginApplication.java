package com.immdream.login;

import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 登录系统
 */
@SpringBootApplication(scanBasePackages = {"com.immdream"})
@EnableFeignClients
@Api(hidden = true)
public class LoginApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(LoginApplication.class, args);
    }
}
