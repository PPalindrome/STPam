package com.example.server.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 分析挖掘任务参数
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ComputeParam {
    //模型文件名（单文件或压缩包）
    private String modelFileName;
    //数据集相对路径
    private String datasetPath;
    //分布式计算框架
    private String distributeArch;
    //计算命令行
    private String command;
    //输出路径
    private String outpath;

}
