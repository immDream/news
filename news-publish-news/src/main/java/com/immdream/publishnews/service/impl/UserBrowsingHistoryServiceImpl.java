package com.immdream.publishnews.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.immdream.model.domain.user.UserBrowsingHistory;
import com.immdream.publishnews.mapper.UserBrowsingHistoryMapper;
import com.immdream.publishnews.service.IUserBrowsingHistoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author immDream
 * @since 2023-04-24
 */
@Service
public class UserBrowsingHistoryServiceImpl extends ServiceImpl<UserBrowsingHistoryMapper, UserBrowsingHistory> implements IUserBrowsingHistoryService {

}
