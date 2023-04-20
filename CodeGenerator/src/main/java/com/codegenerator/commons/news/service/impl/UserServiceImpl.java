package com.codegenerator.commons.news.service.impl;

import com.codegenerator.commons.news.entity.User;
import com.codegenerator.commons.news.mapper.UserMapper;
import com.codegenerator.commons.news.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
