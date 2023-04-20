package com.immdream.commons.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ribbon 的自定义负载均衡规则
 * be能放在 @ComponentScan 能扫描的目录下
 * <p>
 * news com.immdream.config
 *
 * @author immDream
 * @date 2023/04/10/13:14
 * @since 1.8
 */
@Configuration
public class LoadBalancerRule {

    @Bean
    public IRule myRule() {
        return new RandomRule();
    }
}
