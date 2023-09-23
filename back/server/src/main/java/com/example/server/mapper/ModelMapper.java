package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.Model;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author PanHuiWen
 * @since 2022-05-06
 */

@Mapper
public interface ModelMapper extends BaseMapper<Model> {
    /**
     * 查找模型名称
     */
    @Select("SELECT mname FROM model")
    List<String> getModelName();
    
}
