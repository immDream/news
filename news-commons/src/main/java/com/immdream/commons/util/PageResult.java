package com.immdream.commons.util;

import com.github.pagehelper.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 定义一个用于分页数据封装的工具类
 * <p>
 * shop-erp-after com.immdream.product.erp.util
 *
 * @author immDream
 * @date 2022/08/10/23:31
 * @since 1.8
 */
@Data
public class PageResult<T> implements Serializable {
    /**
     * 当前页码
     */
    private Integer pageIndex;

    /**
     * 每页显示数据条数
     */
    private Integer pageSize;

    /**
     *  总条数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 当前页的具体数据
     */
    private List<T> rows;

    /**
     * 根据 Page 对象 创建一个用于前端显示用的 PageResult对象
     * @param page
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> create (Page<T> page) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageIndex(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());
        pageResult.setRows(page.getResult());
        return pageResult;
    }
}
