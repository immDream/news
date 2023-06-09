package com.immdream.model.domain.news.dto;

import com.immdream.model.domain.news.News;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * 热点新闻
 * <p>
 * news com.immdream.model.domain.news.dto
 *
 * @author immDream
 * @date 2023/06/06/22:12
 * @since 1.8
 */
@Data
public class HotNewsDTO implements Comparable<HotNewsDTO> {
    News news;
    Integer hot;

    @Override
    public int compareTo(@NotNull HotNewsDTO o) {
        if(this.news.getIsTop()) return -1;
        if(o.news.getIsTop()) return 1;
        return o.hot > this.hot ? 1 : o.hot.equals(this.hot) ? 0 : -1;
    }
}
