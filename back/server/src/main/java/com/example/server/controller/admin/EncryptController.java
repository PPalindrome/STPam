/**
	  * 用于前端用户名、密码、验证码传给后端的时候 在开发者工具中可以查看到 所以进行加密
	  *  */
package com.example.server.controller.admin;

import java.security.NoSuchAlgorithmException;

import com.example.server.pojo.RespBean;
import com.example.server.service.IEncryptService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "EncryptService")
@RestController
@ResponseBody
public class EncryptController {
	    @Autowired
		IEncryptService IEncryptService;
		@PostMapping("/encrypt")
		public RespBean Encrypt() throws NoSuchAlgorithmException {
					return  RespBean.success("生成公钥私钥成功", IEncryptService.genKeyPair());
		}
}

