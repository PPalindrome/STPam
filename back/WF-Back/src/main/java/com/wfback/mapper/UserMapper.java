package com.wfback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wfback.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

}
