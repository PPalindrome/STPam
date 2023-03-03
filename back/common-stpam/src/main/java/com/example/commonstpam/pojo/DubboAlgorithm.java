package com.example.commonstpam.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@TableName("dubboalgorithm")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DubboAlgorithm implements Serializable {

    @TableId(value = "mid", type = IdType.ASSIGN_UUID)
    private int mid;

    @TableField("mname")
    private String mname;
    @TableField("mparam")
    private int mparam;
    @TableField("mdescribe")
    private String mdescribe;
    @TableField("muploadtime")
    private Date muploadtime;
    @TableField("muploadpath")
    private String muploadpath;

}
