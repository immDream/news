package com.immdream.publishnews.feign;

import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.user.User;
import com.immdream.publishnews.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户服务
 *
 * @RequsetParam 确定名称，不用的话参数错误
 * 发现可能是Feign在组装Http请求去调用远端服务时 请求头参数有问题。
 * 所以加上produces = MediaType.APPLICATION_JSON_UTF8_VALUE
 * <p>
 * 其实我们用httpClient.execute()执行调用请求的时候，返回值直接就是Response，这个时候响应还没有被解析，是需要我们自己去解析的，而我们一般都是用InputStream去解析。
 * 但是OpenFeign不是，OpenFeign调用远程服务，我们调用的方法返回值直接就是解析好的对象，例如这里就是ResponseEntity<HikResponse>，
 * 从二进制到对象，那这就必然涉及到一个反序列化的过程，反序列化的过程中是需要类可以被实例化，也就是刚刚的canInstantiate()方法
 * news com.immdream.publishnews.feign
 *
 * @author immDream
 * @date 2023/04/27/20:47
 * @since 1.8
 */
@FeignClient(
        value = "${user.service}",
        fallback = UserClientFeignHystrix.class,
        configuration = {FeignConfig.class}
)
@ResponseBody
public interface UserClient {
    @GetMapping("/usermanager/admin/queryUser")
    JsonResult queryUserById(@RequestParam("id") Integer id);
}
