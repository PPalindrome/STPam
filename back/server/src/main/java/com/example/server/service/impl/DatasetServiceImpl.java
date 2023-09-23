package com.example.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.DatasetMapper;
import com.example.server.pojo.Dataset;
import com.example.server.service.IDatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author PanHuiWen
 * @since 2022-09-06
 */
@Service
public class DatasetServiceImpl extends ServiceImpl<DatasetMapper, Dataset> implements IDatasetService {

    @Autowired
    IDatasetService iDatasetService;
    @Override
    public void InsertDataset(String name,String path,String date,String description) {
        Dataset dataset = new Dataset(name, path, date, description);
        iDatasetService.saveOrUpdate(dataset);
    }

    @Override
    public List<Dataset> selectAll(String name) {
        QueryWrapper<Dataset> queryWrapper = new QueryWrapper<>();
        if(name!=null)
            queryWrapper.like("name",name);
        return iDatasetService.list(queryWrapper);
    }


}
