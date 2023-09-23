package com.example.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户注册实体类
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AdminRegister对象",description = "")
public class AdminRegisterParam {
    @ApiModelProperty(value="用户名",required = true)
    private String username;
    @ApiModelProperty(value="密码",required = true)
    private String password;
    @ApiModelProperty(value="邮箱",required = true)
    private String mail;
}
