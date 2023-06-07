package com.immdream.usermanager.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.immdream.model.domain.user.UserBrowsingHistory;
import com.immdream.model.domain.user.dto.HistoryDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author immDream
 * @since 2023-04-24
 */
public interface IUserBrowsingHistoryService extends IService<UserBrowsingHistory> {

    UserBrowsingHistory getOne(HistoryDTO historyDTO);

    /**
     * 浏览新闻增添记录
     * @return
     */
    boolean saveRecord(HistoryDTO historyDTO);

    boolean updateHistory(HistoryDTO historyDTO);
}
