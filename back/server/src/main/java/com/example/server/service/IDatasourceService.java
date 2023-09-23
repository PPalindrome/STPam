package com.example.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.Dataset;
import com.example.server.pojo.Datasource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface IDatasourceService extends IService<Datasource> {

    /**
     * 根据数据源id切换数据源
     * @return
     */
    List<Datasource> get();
    void changeDb(Datasource datasource) throws Exception;

    /**
     * 插入新的数据源
     * @return
     */
    boolean insertDS(Datasource datasource);

    List<HashMap<String,Object>> thansResultSet2List(ResultSet rs) throws SQLException;
    void defiled_csv(List<HashMap<String, Object>> defiled_info,String fileName);

    Datasource findByDatasourceID(String datasourceID);
}
