package com.immdream.usermanager.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * data = {
 *             'usernum': usernum,
 *             'readnum': readnum,
 *             'newsnum': newsnum,
 *             'recnum': recnum,
 *             'comnum': comnum,
 *             'statistical': statistical,
 *             'likenum': likenum,
 *             'regionlist': regionlist,
 *         }
 * <p>
 * news com.immdream.usermanager.domain
 *
 * @author immDream
 * @date 2023/05/30/13:44
 * @since 1.8
 */
@Data
public class HomeData {
    private Long usernum;
    private Long readnum;
    private Long newsnum;
    private Long recnum;
    private Long comnum;
    private Long likenum;
    private Map<String, Integer> statistical;
    private Map<String, Long> region;
}
