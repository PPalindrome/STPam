package com.example.server.controller.dataset;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.controller.dataset.dao.DatasetDTO;
import com.example.server.controller.dataset.dao.PageDTO;
import com.example.server.mapper.DatasetMapper;
import com.example.server.pojo.Dataset;
import com.example.server.pojo.Datasource;
import com.example.server.pojo.RespBean;
import com.example.server.service.IDatasetService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/queryDatasets")
public class DatasetController {

    @Autowired
    private IDatasetService iDatasetService;
    private Gson gson=new Gson();

    /**
     * 获取数据库的数据集信息
     */
    @PostMapping("/page")
    @CrossOrigin
    //@RequestParam Integer currentPage,@RequestParam Integer pageSize
    public String queryDatasets(@RequestBody PageDTO pageDTO) throws IOException {
        int currentPage=new Integer(pageDTO.getCurrentPage());
        int pageSize=new Integer(pageDTO.getPageSize());
        PageHelper.startPage(currentPage, pageSize);
        List<Dataset> result = iDatasetService.selectAll(pageDTO.getSearchData());
        return gson.toJson(result);
    }

    @PostMapping("/total")
    public RespBean getTotal(@RequestBody PageDTO pageDTO){
        int total=iDatasetService.selectAll(pageDTO.getSearchData()).size();
        return RespBean.success(total);
    }
}
