package com.immdream.publishnews.feign;

import com.immdream.commons.exception.ErrorCode;
import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.user.dto.HistoryDTO;
import org.springframework.stereotype.Component;

/**
 * 相当于是熔断
 * <p>
 * Cloud-demo1 cloud.demo.client
 *
 * @author immDream
 * @since 1.8
 */
@Component
public class UserClientFeignHystrix implements UserClient {

    @Override
    public JsonResult queryUserById(Integer id) {
        return JsonResult.error(ErrorCode.SERVER_ERROR, "userManager服务异常，请稍后重试");
    }

    @Override
    public JsonResult historyRecord(HistoryDTO historyDTO) {
        return JsonResult.error(ErrorCode.SERVER_ERROR, "userManager服务异常，请稍后重试");
    }

    @Override
    public JsonResult getOneNewsHistoryRecord(HistoryDTO historyDTO) {
        return JsonResult.error(ErrorCode.SERVER_ERROR, "userManager服务异常，请稍后重试");
    }
}
