package com.example.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.controller.datasource.DBContextHolder;
import com.example.server.controller.datasource.DynamicDataSource;
import com.example.server.mapper.DatasourceMapper;
import com.example.server.pojo.Datasource;
import com.example.server.service.IDatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class DatasourceServiceImpl extends ServiceImpl<DatasourceMapper, Datasource> implements IDatasourceService {

    @Autowired
    DatasourceMapper datasourceMapper;
    @Autowired
    private DynamicDataSource dynamicDataSource;


    @Override
    public List<Datasource> get() {
        return null;
    }

    @Override
    public void changeDb(Datasource datasource) throws Exception {

        //默认切换到主数据源,进行整体资源的查找
        DBContextHolder.clearDataSource();
        try{
            System.out.println("需要使用的的数据源已经找到,datasourceId是：" + datasource.getDatasourceID());
            //创建数据源连接&检查 若存在则不需重新创建
            if(dynamicDataSource.createDataSourceWithCheck(datasource)){
                //切换到该数据源
                DBContextHolder.setDataSource(datasource.getDatasourceID());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 插入新的数据源
     * @return
     */
    @Override
    public boolean insertDS(Datasource datasource){
        return datasourceMapper.insertDS(
                datasource.getDatasourceID(),datasource.getUrl(),datasource.getUsername(),datasource.getPassword(),
                datasource.getDatabasetype()
        );
    }

    public boolean createTables(){
        return datasourceMapper.createTables();
    }
}
