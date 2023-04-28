package com.immdream.usermanager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.immdream.commons.exception.ErrorCode;
import com.immdream.commons.util.JsonResult;
import com.immdream.commons.util.PageResult;
import com.immdream.model.domain.BaseDTO;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.user.dto.UserDTO;
import com.immdream.model.domain.user.query.UserPublishNewsQuery;
import com.immdream.model.domain.user.query.UserQuery;
import com.immdream.model.domain.user.request.*;
import com.immdream.usermanager.service.IUserService;
import com.immdream.webapi.user.UserCenterApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 用户中心
 * <p>
 * news com.immdream.usermanager.controller
 *
 * @author immDream
 * @date 2023/04/24/22:16
 * @since 1.8
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserCenterController implements UserCenterApi {
    @Autowired
    private IUserService userService;


    @Override
    @PostMapping("/login")
    public JsonResult<Object> loginByUsername(@RequestBody @Validated({BaseDTO.Select.class}) LoginUserDTORequest loginUserDTORequest) {
        if(Objects.isNull(loginUserDTORequest)) return JsonResult.create(HttpStatus.BAD_REQUEST, "用户名或密码错误", ErrorCode.REQUEST_PARAM_INVALID.getCode(), ErrorCode.REQUEST_PARAM_INVALID.getMessage());
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(loginUserDTORequest, userDTO);
        log.info("[INFO][用户登录] {}用户登录中!", userDTO.getUsername());
        return userService.login(userDTO);
    }

    @Override
    @PostMapping("/loginBytel")
    public JsonResult loginByTel(@RequestBody @Validated({BaseDTO.Select.class}) LoginUserDTORequest loginUserDTORequest) {
        if(Objects.isNull(loginUserDTORequest)) return JsonResult.create(HttpStatus.BAD_REQUEST, "用户名或密码错误", ErrorCode.REQUEST_PARAM_INVALID.getCode(), ErrorCode.REQUEST_PARAM_INVALID.getMessage());
        log.info("[INFO][用户登录] {}用户登录中!", loginUserDTORequest.getUsername());
        return userService.loginByTelephone(loginUserDTORequest);
    }

    @Override
    @PostMapping("/register")
    public JsonResult registerUser(@RequestBody @Validated({BaseDTO.Insert.class}) RegisterUserDTORequest user) {
        if(user == null) return JsonResult.success("用户注册信息必填项未填写!");
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userService.register(userDTO);
    }

    @Override
    @PutMapping("/updateUserInfo/{id}")
    public JsonResult updateInfo(@RequestBody @Validated({BaseDTO.Update.class})
                                                     UserUpdateDTORequest userUpdateDTORequest,
                                 @PathVariable Integer id) {
        int userId = userUpdateDTORequest.getId();
        if(id == 0) return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "要修改的用户参数为空!");
        if(id != userId) return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户错误!");
        log.info("[INFO][用户信息修改]用户id: {}, 正在修改信息", id);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userUpdateDTORequest, userDTO);
        // 更新用户信息
        boolean finish = userService.updateUserInfoById(userDTO);
        if(!finish) {
            log.info("[INFO][用户更新]用户:{}, 信息更新失败!", userUpdateDTORequest.getUsername());
            return JsonResult.error(ErrorCode.REQUEST_ERROR, "信息更新失败!");
        }
        return JsonResult.success("用户信息更新成功!");
    }

    @Override
    @GetMapping("/getBrowsingHistory")
    public JsonResult getBrowsingHistory(@RequestBody UserHistoryDTORequest userHistoryDTORequest) {
        // 判断对象合法性
        if(userHistoryDTORequest == null || userHistoryDTORequest.getId() < 0) {
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户信息错误!");
        }
        log.info("[INFO][浏览记录]用户浏览记录查询");
        return userService.listHistoryViewsById(userHistoryDTORequest.getId());
    }

    @Override
    @PutMapping("/replaceTelephone/{id}")
    public JsonResult replaceTelephone(@PathVariable Integer id,
                                       @RequestBody String telephone) {
        JSONObject t = JSON.parseObject(telephone);
        telephone = t.getString("telephone");
        if(StringUtils.isEmpty(telephone)) {
            log.info("[INFO][换绑手机号]用户: {},换绑手机号失败，手机号为空!", id);
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "手机号不能为空!");
        }
        boolean f = userService.updateTelephoneById(id, telephone);
        if(!f) {
            log.info("[ERROR][换绑手机号]用户: {},换绑手机号失败！", id);
            return JsonResult.error(ErrorCode.SERVER_ERROR, "数据更新失败！");
        }
        log.info("[INFO][换绑手机号]用户:{}, 换绑手机号成功！", id);
        return JsonResult.success("手机号更新成功！");
    }

    @Override
    @PutMapping("/replaceEmail/{id}")
    public JsonResult<Object> replaceEmail(@PathVariable Integer id,
                                           @RequestBody String email) {
        JSONObject t = JSON.parseObject(email);
        email = t.getString("email");
        if(StringUtils.isEmpty(email)) {
            log.info("[INFO][换绑邮箱]用户: {},换绑邮箱失败，邮箱为空!", id);
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "邮箱不能为空!");
        }
        boolean f = userService.updateEmailById(id, email);
        if(!f) {
            log.info("[ERROR][换绑邮箱]用户: {},换绑邮箱失败！", id);
            return JsonResult.error(ErrorCode.SERVER_ERROR, "数据更新失败！");
        }
        log.info("[INFO][换绑邮箱]用户:{}, 换绑邮箱成功！", id);
        return JsonResult.success("邮箱更新成功！");
    }

    @Override
    @PostMapping("/follow")
    public JsonResult followUser(@RequestBody FollowUserDTORequest followUserDTORequest) {
        if(followUserDTORequest == null
                ||followUserDTORequest.getUserId() <  0
                || followUserDTORequest.getAttentionUserId() < 0) {
            log.info("[INFO][关注用户]用户id或被关注用户id为空!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户请求参数为空!");
        }
        boolean f = userService.followUser(followUserDTORequest);
        if(!f) {
            log.info("[INFO][关注用户]关注失败!");
            return JsonResult.error(ErrorCode.SERVER_ERROR, "关注失败!");
        }
        return JsonResult.success("关注成功!", followUserDTORequest.getAttentionUserId());
    }

    @Override
    @DeleteMapping("/unfollow")
    public JsonResult unfollowUser(@RequestBody FollowUserDTORequest followUserDTORequest) {
        if(followUserDTORequest == null
                ||followUserDTORequest.getUserId() <  0
                || followUserDTORequest.getAttentionUserId() < 0) {
            log.info("[INFO][关注用户]用户id或被关注用户id为空!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户请求参数为空!");
        }
        boolean f = userService.followUser(followUserDTORequest);
        if(!f) {
            log.info("[INFO][关注用户]关注失败!");
            return JsonResult.error(ErrorCode.SERVER_ERROR, "关注失败!");
        }
        return JsonResult.success("关注成功!", followUserDTORequest.getAttentionUserId());
    }

    @Override
    @PutMapping("/particular")
    public JsonResult<Object> particularUser(@RequestBody FollowUserDTORequest followUserDTORequest) {
        return null;
    }

    @Override
    @GetMapping("/attentionUsers/{id}")
    public JsonResult getAttentionList(@PathVariable Integer id,
                                       @RequestParam(defaultValue = "1") int pageIndex,
                                       @RequestParam(defaultValue = "10")int pageSize) {
        Page<UserQuery> attentionUserPage = userService.listAttentionUsers(pageIndex, pageSize, id);
        if(attentionUserPage == null) {
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户id不正确！");
        }
        log.info("[INFO][查询发布新闻]用户:{}, 查询发布新闻！", id);
        // 分页数据转换
        PageResult<UserQuery> pageResult = PageResult.create(attentionUserPage);
        return JsonResult.success(pageResult);
    }

    @Override
    @GetMapping("/publishNews/{id}")
    public JsonResult getPublishNewsList(@PathVariable Integer id,
                                         @RequestParam(defaultValue = "1") int pageIndex,
                                         @RequestParam(defaultValue = "10")int pageSize) {
        Page<News> publishNewsPage = userService.listPublishNews(id, pageIndex, pageSize);
        if(publishNewsPage == null) {
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户id不正确！");
        }
        log.info("[INFO][查询发布新闻]用户:{}, 查询发布新闻！", id);
        // 分页数据转换
        PageResult<News> pageResult = PageResult.create(publishNewsPage);
        return JsonResult.success(pageResult);
    }

    /**
     * 新闻发布者删除新闻
     * @param id
     * @param newsId
     * @return
     */
    @Override
    public JsonResult<Object> deletePublishNews(Integer id, String newsId) {
        return null;
    }

    @Override
    @GetMapping("/publishNews")
    public JsonResult queryPublishNewsList(@RequestBody UserPublishNewsQuery userPublishNewsQuery,
                                                   @RequestParam(defaultValue = "1") int pageIndex,
                                                   @RequestParam(defaultValue = "10")int pageSize) {
        Page<News> publishNewsPage = userService.listPublishNewsByNewsName(userPublishNewsQuery, pageIndex, pageSize);
        if(publishNewsPage == null) {
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户id不正确！");
        }
        // 分页数据转换
        PageResult<News> pageResult = PageResult.create(publishNewsPage);
        return JsonResult.success(pageResult);
    }
}
