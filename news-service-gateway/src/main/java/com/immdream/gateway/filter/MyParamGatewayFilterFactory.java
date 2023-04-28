// package com.immdream.gateway.filter;
//
// import org.springframework.cloud.gateway.filter.GatewayFilter;
// import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
// import org.springframework.http.server.reactive.ServerHttpRequest;
// import org.springframework.stereotype.Component;
//
// import java.util.Arrays;
// import java.util.List;
// import java.util.Map;
//
//
// /**
//  * Created with IntelliJ IDEA.
//  * <p>
//  * Cloud-demo1 cloud.demo.filter
//  *
//  * @author immDream
//  * @date 2022/09/10/16:47
//  * @since 1.8
//  */
// @Component
// public class MyParamGatewayFilterFactory extends AbstractGatewayFilterFactory<MyParamGatewayFilterFactory.ParamConfig> {
//
//     public static final String PARAM_NAME = "name";
//
//     public MyParamGatewayFilterFactory() {
//         // 设置过滤器配置的对象类型
//         super(ParamConfig.class);
//     }
//
//     public MyParamGatewayFilterFactory(Class<ParamConfig> configClass) {
//         super(configClass);
//     }
//
//     @Override
//     public GatewayFilter apply(ParamConfig config) {
//         // serverWebExchange：表示服务器处理请求和响应的数据交换的对象
//         // gatewayFilterChain: 网关过滤链
//         return (serverWebExchange, gatewayFilterChain) -> {
//             System.out.println("------------ 自定义过滤器 --------------" + config.name);
//             // 获取请求对象
//             ServerHttpRequest request = serverWebExchange.getRequest();
//             // 获取请求的参数 map
//             Map<String, List<String>> map = request.getQueryParams();
//             System.out.println(map);
//             if(map.containsKey(config.name)) {
//                 List<String> values = map.get(config.name);
//                 for(String value: values) {
//                     System.out.printf("----------局部过滤器-----------%s=%s\n", config.name, value);
//                 }
//             }
//
//             return gatewayFilterChain.filter(serverWebExchange);
//         };
//     }
//
//     @Override
//     public List<String> shortcutFieldOrder() {
//         // 过滤器中需要配置的参数属性名称
//         return Arrays.asList(PARAM_NAME);
//     }
//
//     public static class ParamConfig {
//         private String name;
//
//         public String getName() {
//             return name;
//         }
//
//         public void setName(String name) {
//             this.name = name;
//         }
//
//         @Override
//         public String toString() {
//             return "ParamConfig{" +
//                     "name='" + name + '\'' +
//                     '}';
//         }
//     }
// }
