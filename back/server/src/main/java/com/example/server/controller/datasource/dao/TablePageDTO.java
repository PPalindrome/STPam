package com.example.server.controller.datasource.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TablePageDTO {
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
    /**
     * 所属数据库的名称
     */
    private String databaseName;
    /**
     * 所属数据库的ID
     */
    private String datasourceID;
}
