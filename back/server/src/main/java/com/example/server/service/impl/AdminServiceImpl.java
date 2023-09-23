package com.example.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.server.config.security.JwtTokenUtil;
import com.example.server.mapper.AdminMapper;
import com.example.server.pojo.Admin;
import com.example.server.pojo.RespBean;
import com.example.server.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author PanHuiWen
 * @since 2022-04-07
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;



    /**
     * 登陆之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password,HttpServletRequest request) {
        //登录
        UserDetails userDetails= userDetailsService.loadUserByUsername(username);
        if(null==userDetails||!passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名或密码不正确");
        }
        if(!userDetails.isEnabled()){
            return RespBean.error("账号被禁用");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成token
        String name=username;
        String tokenWithoutName = jwtTokenUtil.generateToken(userDetails);
        String token="?"+name+"?"+tokenWithoutName;
        Map<String,String> tokenMap=new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登陆成功",tokenMap);
    }

    /**
     * 登陆之后返回令牌token
     * @param username
     * @param request
     * @return
     */
    public RespBean getRole(String username, HttpServletRequest request){
        if(username!=null){
            Admin admin=adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username));
//            int role=admin.getRole();
//            if(role==0||role==1){
//                return RespBean.success("拿到角色！！",role);
//            }
            return RespBean.success("拿到角色！！",admin.getRole());
//            return RespBean.success("拿到角色！！",0);
        }
        return RespBean.error("用户名为空！！");
    }

    /**
     * 注册新用户
     * @param username
     * @param password
     * @param mail
     * @param request
     * @return
     */
    @Override
    public RespBean register(String username, String password, String mail, HttpServletRequest request) {
        //判断 username password mail 不能为空
        if (username == null || password == null || mail == null) {
            return RespBean.error("信息填写不正确");
        }
        try {
            Admin admin=new Admin();
            admin.setUsername(username);
            // 密码加密存储
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String bPassword = bCryptPasswordEncoder.encode(password);
            admin.setPassword(bPassword);
            admin.setRole(0);
            admin.setMail(mail);
            admin.setEnabled(true);
            // 写入数据库
            adminMapper.insert(admin);
            System.out.println("新建信息成功");
            return RespBean.success("注册成功");
        } catch (Exception e) {
            // 注册错误
            System.out.println("失败");
            return RespBean.error("注册失败");
        }
    }


    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",true));
    }

    /**
     * 修改用户信息
     * @param username
     * @param password
     * @param mail
     * @param request
     * @return
     */
    @Override
    public RespBean userSetting(String username, String password, String mail, HttpServletRequest request) {
        UserDetails userDetails= userDetailsService.loadUserByUsername(username);
        if(null==userDetails||password==null||mail==null){
            return RespBean.error("用户名不存在或信息输入不完整");
        }
        if(!userDetails.isEnabled()){
            return RespBean.error("账号被禁用");
        }
        Admin admin=new Admin();
        admin.setUsername(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String bPassword = bCryptPasswordEncoder.encode(password);
        admin.setPassword(bPassword);
        admin.setMail(mail);
        admin.setEnabled(true);
        UpdateWrapper<Admin> adminUpdateWrapper = new UpdateWrapper<>();
        System.out.println("查找是否有匹配的用户名");
        adminUpdateWrapper.eq("username", username);
        System.out.println("开始更新");
        adminMapper.update(admin,adminUpdateWrapper);
        return RespBean.success("信息修改成功");
    }
}
