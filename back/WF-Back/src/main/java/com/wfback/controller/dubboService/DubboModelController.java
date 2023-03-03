package com.wfback.controller.dubboService;

import com.example.commonstpam.pojo.DubboAlgorithm;
import com.example.commonstpam.pojo.RespBean;
import com.example.commonstpam.service.IAlgorithmService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DubboModelController {

    @DubboReference(version = "2.0.0")
    private IAlgorithmService algorithmService;

    //获取user表所有数据
    @GetMapping("/getAllModel")
    public List<DubboAlgorithm> getAllModel() {
        List<DubboAlgorithm> algorithm = algorithmService.getAllModel();
        System.out.println(algorithm);
        return algorithm;
    }

    @PostMapping("/insertModel")
    public RespBean insertModel(DubboAlgorithm dubboAlgorithm) {
        algorithmService.insertModel(dubboAlgorithm);
        return RespBean.success("发布成功！", dubboAlgorithm);
    }

    //删除数据
    @PostMapping("/deleteModel")
    public RespBean deleteModel(DubboAlgorithm dubboAlgorithm) {
        algorithmService.deleteModel(dubboAlgorithm);
        return RespBean.success("删除成功！", dubboAlgorithm);

    }
    //修改数据
    @PostMapping("/updateModel")
    public RespBean updateModel(DubboAlgorithm dubboAlgorithm) {
        algorithmService.updateModel(dubboAlgorithm);
        return RespBean.success("更新成功！",dubboAlgorithm);
    }
}
