package com.immdream.usermanager.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.user.Admin;
import com.immdream.model.domain.user.dto.AdminDTO;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 管理员用户登录
     * @param adminDTO
     * @return
     */
    JsonResult login(AdminDTO adminDTO);

    /**
     * 管理员用户增加
     * @param adminDTO
     * @return
     */
    boolean addAdmin(AdminDTO adminDTO);

    /**
     * 管理员用户删除
     * @param id
     * @return
     */
    boolean delAdminById(Integer id);

    /**
     * 更改权限
     * @param adminDTO
     * @return
     */
    boolean authChange(AdminDTO adminDTO);

    /**
     * 分页查询管理员
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<Admin> listAdmin(int pageIndex, int pageSize);
}
