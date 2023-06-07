package com.immdream.usermanager.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.immdream.model.domain.user.UserRegion;
import com.immdream.usermanager.config.MapResultHandler;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
public interface RegionMapper extends BaseMapper<UserRegion> {
    public void getRegionNum(MapResultHandler resultHandler);
}
