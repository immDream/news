package com.immdream.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * Cloud-demo1 cloud.demo.filter
 *
 * @author immDream
 * @date 2022/09/11/18:19
 * @since 1.8
 */
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("---------------全局过滤器---------------");
        System.out.println(exchange.getRequest().getURI());
        MultiValueMap<String, String> params = exchange.getRequest().getQueryParams();
        if(params.isEmpty()) {
            System.out.println("参数为空");
        } else {
            for(Map.Entry<String, List<String>> entry : params.entrySet()) {
                System.out.println(entry.getKey() + ":" + Arrays.toString(entry.getValue().toArray()));
            }
        }
        // return chain.filter(exchange);
        // return exchange.getResponse().setComplete();        // 用来标识请求完成
        return chain.filter(exchange);
    }

    /**
     * 用来设置过滤器执行的顺序
     * @return
     */
    @Override
    public int getOrder() {
        // 值越小，优先级越高
        return 0;
    }
}
