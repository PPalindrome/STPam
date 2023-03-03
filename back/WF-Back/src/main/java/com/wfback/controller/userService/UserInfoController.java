package com.wfback.controller.userService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.wfback.mapper.UserMapper;
import com.wfback.pojo.RespBean;
import com.wfback.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserInfoController {
    @Autowired
    private UserMapper userMapper;
    private Gson gson=new Gson();

    @GetMapping("/userInfo")
    public String userInfo(){
        List<User> users=userMapper.selectList(null);
        return gson.toJson(users);
    }
    @PostMapping("/addUser")
    public void addUser(@RequestBody User user){
        userMapper.insert(user);
    }
    @PostMapping("/deleteUser")
    public void deleteUser(@RequestBody User user){
        userMapper.deleteById(user);
    }
    @PostMapping("/updateUser")
    public void updateUser(@RequestBody User user){
        userMapper.updateById(user);
    }

    @PostMapping("/userLogin")
    public RespBean userLogin(@RequestBody User user){

        QueryWrapper<User> userQueryWrapper =new QueryWrapper<>();
        System.out.println("userQueryWrapper!!::"+userQueryWrapper);
        userQueryWrapper.setEntity(user);
        System.out.println("user!!::"+user);
        User userSelected=userMapper.selectOne(userQueryWrapper);
        System.out.println("userSelect!!::"+userSelected);
        if (userSelected==null){
            System.out.println("error");
            return RespBean.error("用户名或密码错误！");
        }
        return RespBean.success("登陆成功！");

    }
    @PostMapping("/userRegister")
    public RespBean userRegister(@RequestBody User user){
        try{
            userMapper.insert(user);
            return RespBean.success("注册成功！",user.getUsername());
        } catch (Exception e) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!"+e);
            e.printStackTrace();
            return RespBean.error("用户名已存在！",user.getUsername());
        }

    }
}
