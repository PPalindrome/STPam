package com.wfback.controller;


//import com.example.IDubboServiceTest;
import com.google.gson.Gson;
import com.wfback.mapper.WPSModelMapper;
import com.wfback.pojo.WPSModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class testController {
    @Autowired
    private WPSModelMapper wpsModelMapper;
    private Gson gson=new Gson();

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/test")
    public WPSModel queryWPSModels() {
//        List<WPSModel> wpsModels = wpsModelMapper.selectList(null);
//        return gson.toJson(wpsModels);
//
//        List<ServiceInstance> instances = discoveryClient.getInstances("wps-service");
//        int index = new Random().nextInt(instances.size());
//        ServiceInstance serviceInstance = instances.get(index);
//        String url = serviceInstance.getHost() + ":" +
//                serviceInstance.getPort();
//        log.info(">>从nacos中获取到的微服务地址为:" + url);

        //直接使用微服务名字， 从nacos中获取服务地址
//        String url = "wps-service";
        //通过restTemplate调用商品微服务


        WPSModel wpsModels = restTemplate.getForObject("http://wps-service//wpsModel/1" , WPSModel.class);
        log.info(">>模型信息， 查询结果:" + gson.toJson(wpsModels));

        return wpsModels;


    }


}
