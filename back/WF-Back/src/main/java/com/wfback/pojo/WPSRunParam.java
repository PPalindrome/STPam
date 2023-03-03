package com.wfback.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "WPS算法参数",description = "")
public class WPSRunParam {
    @ApiModelProperty(value="算法名称",required = true)
    private String modelName;
    @ApiModelProperty(value="参数信息",required = true)
    private String params;

    private String filePath;
}
