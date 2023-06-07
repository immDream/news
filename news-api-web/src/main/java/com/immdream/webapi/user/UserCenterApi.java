package com.immdream.webapi.user;

import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.user.query.UserPublishNewsQuery;
import com.immdream.model.domain.user.request.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户中心
 * <p>
 * news com.immdream.webapi.user
 *
 * @author immDream
 * @date 2023/04/24/22:32
 * @since 1.8
 */
@Api(value = "用户中心")
public interface UserCenterApi {

    @ApiOperation("用户名登录")
    JsonResult loginByUsername(LoginUserDTORequest loginUserDTORequest);

    @ApiOperation("手机号登录")
    JsonResult loginByTel(LoginUserDTORequest loginUserDTORequest);

    @ApiOperation("用户注册")
    JsonResult<Object> registerUser(RegisterUserDTORequest user);

    @ApiOperation("更新个人信息")
    JsonResult<Object> updateInfo(UserUpdateDTORequest userUpdateDTORequest, Integer id);

    @ApiOperation("查询历史记录")
    JsonResult<Object> getBrowsingHistory(Integer id);

    @ApiOperation("查询历史记录")
    JsonResult<Object> getBrowsingHistory(UserHistoryDTORequest userHistoryDTORequest);

    @ApiOperation("重新绑定手机号")
    JsonResult<Object> replaceTelephone(Integer id, String telephone);

    @ApiOperation("重新绑定邮箱")
    JsonResult<Object> replaceEmail(Integer id, String email);

    @ApiOperation("关注用户")
    JsonResult<Object> followUser(FollowUserDTORequest followUserDTORequest);

    @ApiOperation("取消关注")
    JsonResult<Object> unfollowUser(FollowUserDTORequest followUserDTORequest);

    @ApiOperation("特别关心")
    JsonResult<Object> particularUser(FollowUserDTORequest followUserDTORequest);

    @ApiOperation("查看关注列表")
    JsonResult<Object> getAttentionList(Integer id, int pageIndex, int pageSize);

    @ApiOperation("查看当前用户发布的新闻列表")
    JsonResult<Object> getPublishNewsList(Integer id, int pageIndex, int pageSize);

    @ApiOperation("查看当前用户发布的新闻列表")
    JsonResult<Object> deletePublishNews(Integer id, String newsId);

    @ApiOperation("根据关键词查看当前用户发布的新闻")
    JsonResult<Object> queryPublishNewsList(UserPublishNewsQuery userPublishNewsQuery, int pageIndex, int pageSize);
}
