package com.immdream.usermanager.service.impl;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.immdream.model.domain.user.UserBrowsingHistory;
import com.immdream.model.domain.user.dto.HistoryDTO;
import com.immdream.usermanager.mapper.UserBrowsingHistoryMapper;
import com.immdream.usermanager.service.IUserBrowsingHistoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

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

    @Resource
    private UserBrowsingHistoryMapper userBrowsingHistoryMapper;

    @Override
    public UserBrowsingHistory getOne(HistoryDTO historyDTO) {
        UserBrowsingHistory history = new LambdaQueryChainWrapper<>(userBrowsingHistoryMapper)
                .eq(UserBrowsingHistory::getUserId, historyDTO.getUserId())
                .eq(UserBrowsingHistory::getNewsId, historyDTO.getNewsId())
                .one();
        return history;
    }

    @Override
    public boolean saveRecord(HistoryDTO historyDTO) {
        UserBrowsingHistory history = new UserBrowsingHistory();
        BeanUtils.copyProperties(historyDTO, history);
        return this.save(history);
    }

    @Override
    public boolean updateHistory(HistoryDTO historyDTO) {
        UserBrowsingHistory history = this.getOne(historyDTO);
        if(Objects.isNull(history)) {
            history = new UserBrowsingHistory();
            BeanUtils.copyProperties(historyDTO, history);
            return this.save(history);
        }
        return new LambdaUpdateChainWrapper<>(userBrowsingHistoryMapper)
                .set(UserBrowsingHistory::getComment, historyDTO.getComment())
                .set(UserBrowsingHistory::getJoke, historyDTO.getJoke())
                .set(UserBrowsingHistory::getDeleted, historyDTO.getDeleted())
                .set(UserBrowsingHistory::getCollect, historyDTO.getCollect())
                .set(UserBrowsingHistory::getUninterested, historyDTO.getUninterested())
                .set(UserBrowsingHistory::getUpdateTime, new Date())
                .eq(UserBrowsingHistory::getUserId, historyDTO.getUserId())
                .eq(UserBrowsingHistory::getNewsId, historyDTO.getNewsId())
                .update();
    }
}
