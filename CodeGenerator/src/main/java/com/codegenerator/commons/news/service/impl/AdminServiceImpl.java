package com.codegenerator.commons.news.service.impl;

import com.codegenerator.commons.news.entity.Admin;
import com.codegenerator.commons.news.mapper.AdminMapper;
import com.codegenerator.commons.news.service.IAdminService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
