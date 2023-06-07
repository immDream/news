package com.immdream.usermanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.immdream.model.domain.user.Role;
import com.immdream.model.domain.user.UserRegion;
import com.immdream.usermanager.config.MapResultHandler;
import com.immdream.usermanager.mapper.RegionMapper;
import com.immdream.usermanager.mapper.RoleMapper;
import com.immdream.usermanager.service.IRegionService;
import com.immdream.usermanager.service.IRoleService;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, UserRegion> implements IRegionService {

    @Resource
    private RegionMapper regionMapper;

    @Override
    public Map<String, Long> getRegionNum() {
        MapResultHandler handler = new MapResultHandler();
        regionMapper.getRegionNum(handler);
        Map<String, Long> map = handler.getMappedResults();
        if(map != null) {
            System.out.println(map.toString());
        }
        return map;
    }
}
