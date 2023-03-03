package com.wfback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wfback.pojo.WPSModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author phw
 * @since 2022-07-20
 */
@Mapper
@Repository
public interface WPSModelMapper extends BaseMapper<WPSModel> {

}
