package com.immdream.publishnews.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.publishnews.domain
 *
 * @author immDream
 * @date 2023/06/08/23:55
 * @since 1.8
 */
@Data
@ToString
@EqualsAndHashCode
public class UserFeature {
    /**
     * 用户 id
     */
    private Integer userId;

    /**
     * 该用户对某一类新闻的特征值
     * key: newType, value: featureValue
     */
    private Map<Integer, Integer> newsTypeFeature;
}
