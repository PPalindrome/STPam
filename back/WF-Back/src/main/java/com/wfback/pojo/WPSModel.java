package com.wfback.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("wpsmodel")
@Data
public class WPSModel implements Serializable {

    @TableId(value = "mid", type = IdType.AUTO)
    private int mid;

    @TableField("mname")
    private String mname;
    @TableField("mparam")
    private int mparam;
    @TableField("mdescribe")
    private String mdescribe;
    @TableField("muploadtime")
    private String muploadtime;
    @TableField("muploadlocation")
    private String muploadlocation;

}
