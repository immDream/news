package com.immdream.usermanager.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.pagehelper.Page;
import com.immdream.commons.exception.ErrorCode;
import com.immdream.commons.util.JsonResult;
import com.immdream.commons.util.PageResult;
import com.immdream.model.domain.BaseDTO;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.user.Admin;
import com.immdream.model.domain.user.User;
import com.immdream.model.domain.user.UserBrowsingHistory;
import com.immdream.model.domain.user.dto.AdminDTO;
import com.immdream.model.domain.user.dto.HistoryDTO;
import com.immdream.model.domain.user.dto.UserDTO;
import com.immdream.model.domain.user.query.UserQuery;
import com.immdream.model.domain.user.request.LoginUserDTORequest;
import com.immdream.model.domain.user.request.RegisterUserDTORequest;
import com.immdream.model.domain.user.request.UserUpdateDTORequest;
import com.immdream.usermanager.domain.HomeData;
import com.immdream.usermanager.service.*;
import com.immdream.webapi.user.UserManagerApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * <p>
 *  用户管理服务
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class UserManagerController implements UserManagerApi {

    @Autowired
    private IUserService userService;

    @Autowired
    private IAdminService adminService;

    @Autowired
    private INewsService newsService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IRegionService regionService;

    @Autowired
    private IUserBrowsingHistoryService historyService;

    /**
     * @return
     */
    @GetMapping("/homedata")
    public JsonResult getHomeData() {
        HomeData homeData = new HomeData();
        homeData.setUsernum(userService.count());
        homeData.setNewsnum(newsService.count());
        homeData.setReadnum(historyService.count());
        homeData.setLikenum(historyService.count(new QueryWrapper<UserBrowsingHistory>().eq("is_joke", true)));
        homeData.setComnum(commentService.count());
        homeData.setRecnum(homeData.getUsernum() * 3);
        homeData.setRegion(regionService.getRegionNum());
        return JsonResult.success(homeData);
    }

    /**
     * 管理员用户登录
     * @param adminDTO
     * @return
     */
    @PostMapping("/login")
    public JsonResult login(@RequestBody AdminDTO adminDTO) {
        if(Objects.isNull(adminDTO)) return JsonResult.create(HttpStatus.BAD_REQUEST, "用户名或密码错误", ErrorCode.REQUEST_PARAM_INVALID.getCode(), ErrorCode.REQUEST_PARAM_INVALID.getMessage());
        log.info("[INFO][用户登录] {}用户登录中!", adminDTO.getAdminUsername());
        return adminService.login(adminDTO);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    @PostMapping("/register")
    public JsonResult registerUser(@Validated({BaseDTO.Insert.class}) @RequestBody RegisterUserDTORequest user) {
        if(user == null) return JsonResult.success("用户注册信息必填项未填写!");
        if(!user.getPassword().equals(user.getPassword2())) return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户两次输入密码不一致！");
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userService.register(userDTO);
    }

    /**
     * 用户添加 - 管理员测试操作
     * @param user
     * @return
     */
    @Override
    @PostMapping("/add_user")
    public JsonResult addUser(@RequestBody @Validated({BaseDTO.Insert.class}) UserUpdateDTORequest user) {
        RegisterUserDTORequest registerUserDTORequest = new RegisterUserDTORequest();
        BeanUtils.copyProperties(user, registerUserDTORequest);
        return registerUser(registerUserDTORequest);
    }

    /**
     * 用户删除 - 返回被删除的用户名
     *
     * @param username 要删除的用户名
     * @return
     */
    @Override
    @DeleteMapping("/delete_user/{username}")
    public JsonResult deleteUser(@PathVariable String username) {
        log.info("[INFO][用户删除] 用户名：{}，正在进行删除！", username);
        if(StringUtils.isEmpty(username)) {
            log.info("[INFO][用户删除] 用户名为空！");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_INVALID, "请求用户名不能为空!");
        }
        boolean finish = userService.deleteUserByUsername(username);
        if(!finish) {
            log.info("[INFO][用户删除] 用户名：{}，删除业务失败！", username);
            return JsonResult.error(ErrorCode.ADMIN_DELETE_USER_ERROR, "用户删除失败!");
        }
        log.info("[INFO][用户删除] 用户名：{}，删除成功！", username);
        return JsonResult.success("用户删除成功！", username);
    }

    /**
     * 更改用户信息
     * @param id 要更改的用户的 id
     * @param newUserInfo 更改后的用户信息
     * @return
     */
    @Override
    @PutMapping("/update_user_info/{id}")
    public JsonResult updateUserInfo(@PathVariable Integer id,
                                     @RequestBody @Validated({BaseDTO.Update.class}) UserUpdateDTORequest newUserInfo) {
        if(id == 0 || Objects.isNull(newUserInfo))
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "要修改的用户参数为空!");
        // if(StringUtils.isEmpty(newUserInfo.getTelephone())) return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户手机号禁止为空！");
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(newUserInfo, userDTO);
        // 更新用户信息
        boolean finish = userService.updateUserInfoById(userDTO);
        if(!finish) {
            log.info("[INFO][用户更新]用户:{}, 信息更新失败!", newUserInfo.getUsername());
        }
        return JsonResult.success("用户信息更新成功!");
    }

    @Override
    @GetMapping("/queryUser")
    public JsonResult getUser(Integer id) {
        if(id <= 0) return JsonResult.success("用户不存在!");
        return JsonResult.success(userService.getById(id));
    }

    /**
     * 根据条件搜索用户
     * 手机号 id，email 用户名
     * @return
     */
    @Override
    @GetMapping("/getUser")
    public JsonResult<Object> getUser(String keyword, String condition) {
        System.out.println(keyword + "  " + condition);
        User userCondition = new User();
        switch (condition) {
            case "id":
                userCondition.setId(Integer.valueOf(keyword));
                break;
            case "telephone":
                userCondition.setTelephone(keyword);
                break;
            case "email":
                userCondition.setEmail(keyword);
                break;
            default:
                userCondition.setUsername(keyword);
                break;
        }
        return JsonResult.success(userService.selectUserListByCondition(userCondition));
    }

    @Override
    @GetMapping("/getUserList")
    public JsonResult<Object> getUserList(@RequestParam(defaultValue = "1") int pageIndex,
                                          @RequestParam(defaultValue = "10") int pageSize) {
        Page<User> usersPage = userService.listUsers(pageIndex, pageSize, null);
        // 分页数据转换
        PageResult<User> pageResult = PageResult.create(usersPage);
        return JsonResult.success(pageResult);
    }

    @GetMapping("/userList")
    public JsonResult<Object> getUserList() {
        return JsonResult.success(userService.list());
    }

    @Override
    @PostMapping("/addAdmin")
    public JsonResult<Object> addAdmin(AdminDTO admin) {
        boolean row = adminService.addAdmin(admin);
        return row ? JsonResult.success(admin) : JsonResult.success("用户添加失败");
    }

    @Override
    @DeleteMapping("/deleteAdmin/{id}")
    public JsonResult<Object> deleteAdmin(@PathVariable Integer id) {
        return adminService.delAdminById(id) ?
                JsonResult.success("用户删除成功") : JsonResult.success("用户删除失败");
    }

    @Override
    @PutMapping("/updateRole")
    public JsonResult<Object> updateAdminRole(AdminDTO admin) {
        return adminService.authChange(admin) ? JsonResult.success("用户权限更改成功") : JsonResult.success("用户权限更改失败");
    }

    @Override
    @GetMapping("/getAdminList")
    public JsonResult<Object> getAdminList(@RequestParam(defaultValue = "1") int pageIndex,
                                           @RequestParam(defaultValue = "10") int pageSize) {
        Page<Admin> adminPage = adminService.listAdmin(pageIndex, pageSize);
        // 分页数据转换
        PageResult<Admin> pageResult = PageResult.create(adminPage);
        return JsonResult.success(pageResult);
    }

    @Override
    @PutMapping("/banUser/{id}")
    public JsonResult<Object> banUser(@PathVariable String id) {
        boolean finish = userService.banUserById(id);
        // 通知用户
        if(!finish) {
            log.info("[INFO][封禁用户]封禁用户id：{}失败", id);
            return JsonResult.error(ErrorCode.SERVER_ERROR, "用户封禁失败！");
        }
        log.info("[INFO][封禁用户]封禁用户id：{}成功", id);
        return JsonResult.success("操作成功", id);
    }

    @Override
    @PutMapping("/unBanUser/{id}")
    public JsonResult<Object> unBanUserInfo(@PathVariable String id) {
        boolean finish = userService.unBanUserById(id);
        if(!finish) {
            log.info("[INFO][解封用户]解封用户id：{}失败", id);
            return JsonResult.error(ErrorCode.SERVER_ERROR, "用户解封失败！");
        }
        log.info("[INFO][解封用户]解封用户id：{}成功", id);
        return JsonResult.success("操作成功", id);
    }

    @Override
    @PostMapping("/history")
    public JsonResult<Object> historyRecord(@RequestBody HistoryDTO historyDTO) {
        UserBrowsingHistory history = historyService.getOne(historyDTO);
        boolean finished = false;
        if(Objects.isNull(history)) {
            log.info("[INFO][浏览记录]用户没有浏览过新闻{}", historyDTO.getNewsId());
            finished = historyService.saveRecord(historyDTO);
            if(finished) log.info("[INFO][浏览记录]插入对应浏览记录");
        } else {
            log.info("[INFO][浏览记录]更新用户浏览记录");
            finished = historyService.updateHistory(historyDTO);
        }
        if(finished) {
            history = historyService.getOne(historyDTO);
            return JsonResult.success("用户浏览记录插入成功!", history);
        } else {
            return JsonResult.error(ErrorCode.SERVER_ERROR, "更新浏览记录失败");
        }
    }

    @Override
    @GetMapping("/getOneNewsHistoryRecord")
    public JsonResult<Object> getHistoryRecord(@RequestBody HistoryDTO historyDTO) {
        UserBrowsingHistory history = historyService.getOne(historyDTO);
        if(Objects.isNull(history)) {
            return JsonResult.error(ErrorCode.SERVER_ERROR, "查询历史记录失败");
        }
        return JsonResult.success(history);
    }
}
