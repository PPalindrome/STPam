package com.example.server.controller.deepModel;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.mapper.ModelMapper;
import com.example.server.pojo.Admin;
import com.example.server.pojo.Model;
import com.example.server.pojo.RespBean;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author PanHuiWen
 * @since 2022-05-06
 */
@RestController
public class ModelController {
    @Autowired
    private ModelMapper modelMapper;

    private Gson gson=new Gson();

    /**
     * 上传模型信息到数据库
     */
    @PostMapping("/save")
    public RespBean save(@RequestBody Model file) throws IOException{
        try{
            if(modelMapper.selectOne(new QueryWrapper<Model>().eq("filename",file.getFilename()))!=null)
            {
                return RespBean.error("已存在该模型");
            }
            Model model=new Model();
            model.setFilename(file.getFilename());
            model.setMname(file.getMname());
            model.setVersion(file.getVersion());
            model.setAuthor(file.getAuthor());
            model.setCommand(file.getCommand());
            model.setOutpath(file.getOutpath());
            System.out.println(model);
            modelMapper.insert(model);
            return RespBean.success("成功保存到数据库");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("保存到数据库失败！！");
        }
    }
    /**
     * 获取数据库的模型信息
     */
    @PostMapping("/queryModels")
    @CrossOrigin
    public String queryModels() throws IOException{
        try{
            List<Model> m=modelMapper.selectList(null);
            return gson.toJson(m);
        }catch (Exception e){
            e.printStackTrace();
            return "没找到默认模型！！";
        }
    }
}
