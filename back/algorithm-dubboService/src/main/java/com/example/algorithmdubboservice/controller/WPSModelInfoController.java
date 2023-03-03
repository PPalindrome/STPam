package com.example.algorithmdubboservice.controller;


import com.google.gson.Gson;
import com.wfback.mapper.WPSModelMapper;
import com.wfback.pojo.RespBean;
import com.wfback.pojo.WPSModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin//可以加在类上，也可以加到方法上
public class WPSModelInfoController {
    @Autowired
    private WPSModelMapper wpsModelMapper;
    private Gson gson=new Gson();


    @GetMapping("/wpsModel/{mid}")
    @CrossOrigin//可以加在类上，也可以加到方法上
    public WPSModel wpsModel(@PathVariable("mid") Integer mid) {
        log.info("负载均衡触发测试");
        List<WPSModel> wpsModels = wpsModelMapper.selectList(null);
        WPSModel wpsModel=wpsModels.get(mid);
        return wpsModel;
    }

    @GetMapping("/queryWPSModels")
    @CrossOrigin//可以加在类上，也可以加到方法上
    public String queryWPSModels() {
        List<WPSModel> wpsModels = wpsModelMapper.selectList(null);

        return gson.toJson(wpsModels);
    }

    @PostMapping("/addWPSModels")
    public RespBean addWPSModels(@RequestBody WPSModel wpsModel){
        try{
            System.out.println("wpsModel:"+wpsModel);
            wpsModelMapper.insert(wpsModel);
            return RespBean.success("成功啦");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("错误！！！");
        }

    }

    @PostMapping("/deleteWPSModels")
    public void deleteWPSModels(@RequestBody WPSModel wpsModel){
        wpsModelMapper.deleteById(wpsModel);
    }

    @PostMapping("/updateWPSModels")
    public void updateWPSModels(@RequestBody WPSModel wpsModel){
        wpsModelMapper.updateById(wpsModel);
    }


}
