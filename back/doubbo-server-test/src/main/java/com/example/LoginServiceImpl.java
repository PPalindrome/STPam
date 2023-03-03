package com.example;

import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class LoginServiceImpl implements IDubboServiceTest {

    @Override
    public String test(String name, String id) {
        // 业务逻辑
        if(name.equals("phw")&&id.equals("1")){
            return "success";
        }
        return "failed";
    }
}
