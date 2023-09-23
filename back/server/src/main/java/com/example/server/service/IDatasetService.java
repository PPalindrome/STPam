package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.controller.dataset.dao.DatasetDTO;
import com.example.server.mapper.DatasetMapper;
import com.example.server.pojo.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author PanHuiWen
 * @since 2022-09-06
 */
@Service
public interface IDatasetService extends IService<Dataset> {

    void  InsertDataset(String name,String path,String date,String description);
    List<Dataset> selectAll(String name);
}
