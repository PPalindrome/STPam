package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.Dataset;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author PanHuiWen
 * @since 2022-05-06
 */

@Mapper
public interface DatasetMapper extends BaseMapper<Dataset> {

    
}
