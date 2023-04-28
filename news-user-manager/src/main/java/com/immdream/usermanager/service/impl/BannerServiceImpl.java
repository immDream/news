package com.immdream.usermanager.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.immdream.model.domain.news.Banner;
import com.immdream.usermanager.mapper.BannerMapper;
import com.immdream.usermanager.service.IBannerService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

}
