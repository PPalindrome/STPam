package com.example.server.controller.dataset;

import com.example.server.mapper.DatasetMapper;
import com.example.server.pojo.Dataset;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class DatasetController {

    @Autowired
    private DatasetMapper datasetMapper;

    private Gson gson=new Gson();

    /**
     * 获取数据库的数据集信息
     */
    @PostMapping("/queryDatasets")
    @CrossOrigin
    public String queryDatasets() throws IOException {
        try{
            List<Dataset> m=datasetMapper.selectList(null);
            return gson.toJson(m);
        }catch (Exception e){
            e.printStackTrace();
            return "没找到数据集！！";
        }
    }
}
