package com.example.algorithmdubboservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.commonstpam.pojo.DubboAlgorithm;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlgorithmMapper extends BaseMapper<DubboAlgorithm> {
}
