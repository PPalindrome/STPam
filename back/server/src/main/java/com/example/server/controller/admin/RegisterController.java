package com.example.server.controller.admin;

import com.example.server.pojo.AdminRegisterParam;
import com.example.server.pojo.RespBean;
import com.example.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "RegisterController")
@RestController
public class RegisterController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "注册新用户")
    @PostMapping("/register")
    public RespBean register(@RequestBody AdminRegisterParam adminRegisterParam, HttpServletRequest request){
        return adminService.register(adminRegisterParam.getUsername(),adminRegisterParam.getPassword(),adminRegisterParam.getMail(),request);
    }
}
