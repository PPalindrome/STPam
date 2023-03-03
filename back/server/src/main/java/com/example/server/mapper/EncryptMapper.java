package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.AdminLoginParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EncryptMapper extends BaseMapper<AdminLoginParam>{

}

