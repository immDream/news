package com.immdream.webapi.news;

import com.immdream.commons.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.webapi.news
 *
 * @author immDream
 * @date 2023/06/08/23:45
 * @since 1.8
 */
@Api("新闻推荐")
public interface NewsRecommendApi {

    @ApiOperation("新闻推荐")
    JsonResult<Object> newsRecommend(Integer userId);
}
