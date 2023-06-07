package com.immdream.usermanager.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.immdream.model.domain.user.UserBrowsingHistory;
import com.immdream.usermanager.domain.HistoryNewsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author immDream
 * @since 2023-04-24
 */
public interface UserBrowsingHistoryMapper extends BaseMapper<UserBrowsingHistory> {
    List<HistoryNewsDTO> getHistory(@Param("id") Integer userId);
}
