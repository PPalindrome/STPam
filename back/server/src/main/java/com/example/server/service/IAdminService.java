package com.example.server.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.Admin;
import com.example.server.pojo.RespBean;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author PanHuiWen
 * @since 2022-04-07
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 登陆之后返回令牌token
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean login(String username, String password, HttpServletRequest request);

    /**
     * 登陆之后返回令牌token
     * @param username
     * @param request
     * @return
     */
    RespBean getRole(String username, HttpServletRequest request);

    /**
     * 注册新用户
     * @param username
     * @param password
     * @param mail
     * @param request
     * @return
     */
    RespBean register(String username, String password, String mail,HttpServletRequest request);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);

    /**
     * 修改用户信息
     * @param username
     * @param password
     * @param mail
     * @param request
     * @return
     */
    RespBean userSetting(String username, String password, String mail, HttpServletRequest request);
}
