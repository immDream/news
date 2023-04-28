package com.immdream.publishnews.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * Cloud-demo1 cloud.demo.config
 *
 * @author immDream
 * @date 2022/09/07/21:23
 * @since 1.8
 */
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
