package com.example.server.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.mapper.AdminMapper;
import com.example.server.pojo.Admin;
import com.example.server.pojo.AdminLoginParam;
import com.example.server.pojo.Model;
import com.example.server.pojo.RespBean;
import com.example.server.service.IEncryptService;
import com.example.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.Principal;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

@Api(tags = "LoginController")
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;
    @Autowired
    IEncryptService IEncryptService;
    @ApiOperation(value = "登陆之后返回令牌token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request) throws Exception{
        String username=adminLoginParam.getUsername();
        String password=adminLoginParam.getPassword();
        String privateKey= IEncryptService.selectPrivateKetById(1);
        //64位解码加密后的字符串
        byte[] inputUsername = Base64.decodeBase64(username.getBytes("UTF-8"));
        byte[] inputPassword = Base64.decodeBase64(password.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);

        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outUsername = new String(cipher.doFinal(inputUsername));
        String outPassword = new String(cipher.doFinal(inputPassword));
        return adminService.login(outUsername,outPassword,request);
    }

    /**
     * 获取当前登录用户的角色
     * @param name
     * @return
     */
    @PostMapping("/getRole")
    public RespBean getRole(@RequestBody String name, HttpServletRequest request) {
        int length=name.length();
        String username=name.substring(0,length-1);
        return adminService.getRole(username,request);
    }

    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if (null==principal){
            return null;
        }
        String username = principal.getName();
        System.out.println(username);
        Admin admin = adminService.getAdminByUserName(username);
        admin.setPassword(null);
        //
        return admin;
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功！");
    }
}

