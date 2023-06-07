package com.immdream.usermanager.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.immdream.model.domain.user.UserRegion;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
public interface IRegionService extends IService<UserRegion> {
    public Map<String, Long> getRegionNum();
}
