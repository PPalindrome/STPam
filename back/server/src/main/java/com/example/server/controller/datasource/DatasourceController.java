package com.example.server.controller.datasource;

import com.example.server.mapper.DatasourceMapper;
import com.example.server.mapper.ModelMapper;
import com.example.server.pojo.Datasource;
import com.example.server.pojo.Model;
import com.example.server.pojo.RespBean;
import com.example.server.service.impl.DatasourceServiceImpl;
import com.example.server.service.impl.ModelServiceImpl;
import io.swagger.annotations.Api;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "DatasourceController")
@RestController
@RequestMapping("/data")
@RunWith(SpringRunner.class)
public class DatasourceController {

    @Autowired
    private DatasourceMapper datasourceMapper;
    @Autowired
    private DatasourceServiceImpl datasourceService;
    @Autowired
    private ModelMapper modelMapper;


    /**
     * 注册数据库配置信息
     * 切换至新注册的数据库
     * @return
     */
    @PostMapping("/setting")
    public  RespBean dataSetting(@RequestBody Datasource datasource) throws Exception {
        String id=datasource.getDatasourceID(),username=datasource.getUsername(),password=datasource.getPassword(),url=datasource.getUrl();
        if (id==null||username == null || password == null || url == null) {
            return RespBean.error("缺少必要的配置信息！");
        }
        //判断数据库中是否已有该数据库连接
        List<Datasource> dataSourcesList = datasourceMapper.get();
        for (Datasource ds : dataSourcesList) {
            if (ds.getDatasourceID().equals(id)) {
                return RespBean.error("已经存在该数据源，请选择原有配置！");
            }
        }
        try {
            //插入新的数据源
            datasourceService.insertDS(datasource);
            //切换至新的数据源
            datasourceService.changeDb(datasource);


             //这里要加上数据表参数，即datasource中有个数据表属性要提取
             //"查询model表"替换成获取目标数据表内容
             //下载为表格文件存储在服务器中
             //可以直接作为数据在模型中使用


            //查询model表内容
            List<Model> modelList=modelMapper.selectList(null);
            System.out.println(modelList.toString());

            return RespBean.success("数据源配置成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("数据源配置失败！！");
        }
    }
}
