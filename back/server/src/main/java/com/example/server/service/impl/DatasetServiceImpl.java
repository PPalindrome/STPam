package com.example.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.DatasetMapper;
import com.example.server.pojo.Dataset;
import com.example.server.service.IDatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private DatasetMapper datasetMapper;



}
