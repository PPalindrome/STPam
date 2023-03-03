package com.example.server.controller.settings;

import com.example.server.pojo.Admin;
import com.example.server.pojo.RespBean;
import com.example.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "SettingsController")
@RestController
public class SettingsController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/setting/userSetting")
    public RespBean userSetting(@RequestBody Admin admin, HttpServletRequest request){
        return adminService.userSetting(admin.getUsername(),admin.getPassword(),admin.getMail(),request);
    }
}
