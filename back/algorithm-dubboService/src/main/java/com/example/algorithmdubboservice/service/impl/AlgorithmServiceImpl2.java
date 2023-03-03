package com.example.algorithmdubboservice.service.impl;

import com.example.algorithmdubboservice.mapper.AlgorithmMapper;
import com.example.commonstpam.pojo.DubboAlgorithm;
import com.example.commonstpam.service.IAlgorithmService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

@DubboService(version = "2.0.0")
public class AlgorithmServiceImpl2 implements IAlgorithmService {
    @Autowired
    private AlgorithmMapper algorithmMapper;

    //获取user表所有数据
    @Override
    public List<DubboAlgorithm> getAllModel() {
        List<DubboAlgorithm> algorithm = algorithmMapper.selectList(null);
        System.out.println(algorithm);
        algorithm.add(new DubboAlgorithm(6,"test",2,"asd",new Date(2),""));
        return algorithm;
    }

    @Override
    public DubboAlgorithm insertModel(DubboAlgorithm dubboAlgorithm) {
        algorithmMapper.insert(dubboAlgorithm);
        int result1 = algorithmMapper.insert(dubboAlgorithm);
        System.out.println("返回结果，1代表插入成功，0代表失败："+result1);

        return dubboAlgorithm;
    }

    //删除数据
    @Override
    public DubboAlgorithm deleteModel(DubboAlgorithm dubboAlgorithm) {
        algorithmMapper.deleteById(dubboAlgorithm);
        return dubboAlgorithm;

    }
    //修改数据
    @Override
    public DubboAlgorithm updateModel(DubboAlgorithm dubboAlgorithm) {
        algorithmMapper.updateById(dubboAlgorithm);
        System.out.println("返回的数据是影响的行数"+dubboAlgorithm);
        return dubboAlgorithm;
    }
}
