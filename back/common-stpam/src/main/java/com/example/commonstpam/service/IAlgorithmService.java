package com.example.commonstpam.service;


import com.example.commonstpam.pojo.DubboAlgorithm;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

public interface IAlgorithmService {
    List<DubboAlgorithm> getAllModel();
    DubboAlgorithm insertModel(DubboAlgorithm dubboAlgorithm);
    DubboAlgorithm deleteModel(DubboAlgorithm dubboAlgorithm);
    DubboAlgorithm updateModel(DubboAlgorithm dubboAlgorithm);
}
