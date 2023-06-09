package com.immdream.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关
 *
 */
@SpringBootApplication(scanBasePackages = {"com.immdream.gateway"})
@EnableDiscoveryClient
public class GatewayApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
