package com.immdream.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger config
 * <p>
 * news com.immdream.webapi.config
 *
 * @author immDream
 * @date 2023/04/13/16:27
 * @since 1.8
 */
@EnableSwagger2
@Configuration
public class Swagger2Configuration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("听，世界") // 如果配置多个文档的时候，那么需要配置groupName来分组标识
                .apiInfo(apiInfo())     // 用于生成 API信息
                .select()   // select() 函数返回一个 apiSelectorBuilder 实例，用来控制接口被swagger做成文档
                .apis(RequestHandlerSelectors.basePackage("com.immdream")) // 用于指定扫描哪个包下的接口
                // .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())// 选择所有的API,如果你想只为部分API生成文档，可以配置这里
                .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("NEWS API")      // 定义 API主标题
                .description("Swagger API管理")   // 描述
                .termsOfServiceUrl("")          // 用来定义服务域名
                .version("1.0")                 // 服务版本
                // .contact(new Contact("immdream", "url", "email"))
                // .license("Apache 2.0")
                // .licenseUrl("对应版权Url")
                .build();
    }
}
