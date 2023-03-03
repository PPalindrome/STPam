package com.example.cloudtest.controller;


import com.example.cloudtest.pojo.WPSModel;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Slf4j
@RestController
public class testController {

    private Gson gson=new Gson();

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/test")
    public WPSModel queryWPSModels() {
//        List<WPSModel> wpsModels = wpsModelMapper.selectList(null);
//        return gson.toJson(wpsModels);

        List<ServiceInstance> instances = discoveryClient.getInstances("wps-service");
        log.info("wps服务实例数为：",instances.size());
        int index = new Random().nextInt(instances.size());
        ServiceInstance serviceInstance = instances.get(index);
        String url = serviceInstance.getHost() + ":" +
                serviceInstance.getPort();
        log.info(">>从nacos中获取到的微服务地址为:" + url);

        //直接使用微服务名字， 从nacos中获取服务地址
//        String url = "wps-service";
        //通过restTemplate调用商品微服务
        WPSModel wpsModels = restTemplate.getForObject("http://wps-service//wpsModel/1" , WPSModel.class);
        log.info(">>模型信息， 查询结果:" + gson.toJson(wpsModels));

        return wpsModels;
    }

//    @DubboReference
//    private IAlgorithmService productService;
//    @GetMapping("dubboTest")
//    public String testDubbo(){
//        Product p=productService.getAllModel();
//        return p.toString();
//    }
}
