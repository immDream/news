package com.immdream.usermanager.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.immdream.commons.exception.ErrorCode;
import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.user.Admin;
import com.immdream.model.domain.user.User;
import com.immdream.model.domain.user.dto.AdminDTO;
import com.immdream.model.domain.user.dto.UserDTO;
import com.immdream.usermanager.mapper.AdminMapper;
import com.immdream.usermanager.mapper.RoleMapper;
import com.immdream.usermanager.service.IAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public JsonResult login(AdminDTO adminDTO) {
        Admin user = this.getOneByUsername(adminDTO.getAdminUsername());
        if(user == null) return null;
        if(user.getAdminUsername().equals(adminDTO.getAdminUsername())
                && user.getAdminPassword().equals(adminDTO.getAdminPassword())) {
            BeanUtils.copyProperties(user, adminDTO);
            return JsonResult.success("用户登陆成功!", adminDTO);
        }
        return JsonResult.error(ErrorCode.LOGIN_FAILED, "用户名或密码不正确");
    }

    @Override
    public boolean addAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminDTO, admin);
        admin.setCreateTime(new Date());
        return adminMapper.insert(admin) == 1;
    }

    @Override
    public boolean delAdminById(Integer id) {
        if(id < 0) return false;
        log.info("[INFO][管理员删除业务] 正在删除用户id为{}的用户！", id);
        return this.removeById(id);
    }

    @Override
    public boolean authChange(AdminDTO adminDTO) {
        if(Objects.isNull(adminDTO)) return false;
        Admin user = new Admin();
        BeanUtils.copyProperties(adminDTO, user);
        // 更新用户权限
        return new LambdaUpdateChainWrapper<>(adminMapper)
                .set(Admin::getRoleId, user.getRoleId())
                .eq(Admin::getId, user.getId()).update();
    }

    @Override
    public Page<Admin> listAdmin(int pageIndex, int pageSize) {
        // 开启分页
        Page<Admin> page = PageHelper.startPage(pageIndex, pageSize);
        List<Admin> admins = list();
        if(admins == null || admins.size() == 0) {
            log.info("[INFO][管理员列表]管理员列表为空");
        }
        return page;
    }

    /**
     * 根据用户名查询一名用户
     * @param username
     * @return
     */
    public Admin getOneByUsername(String username) {
        return new LambdaQueryChainWrapper<>(adminMapper)
                .eq(Admin::getAdminUsername, username).one();
    }
}
