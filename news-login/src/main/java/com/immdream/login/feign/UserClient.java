package com.immdream.login.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 用户服务
 * <p>
 * news com.immdream.login.feign
 *
 * @author immDream
 * @date 2023/04/12/23:09
 * @since 1.8
 */
@FeignClient(value = "${user-service.name}")
public interface UserClient {
}
