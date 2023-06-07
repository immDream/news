package com.immdream.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.webapi
 *
 * @author immDream
 * @date 2023/05/15/22:05
 * @since 1.8
 */
@SpringBootApplication(scanBasePackages = {"com.immdream.webapi"})
public class WebApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }
}
