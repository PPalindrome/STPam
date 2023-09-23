package com.example.server.controller.dataset.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {
    /**
     * 总条数
     */
    private int pageSize;
    /**
     * 当前页数据
     */
    private int currentPage;
    /**
     * 当前页查询条件
     */
    private String searchData;
}