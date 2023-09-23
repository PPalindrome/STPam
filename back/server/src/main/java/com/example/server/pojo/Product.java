package com.example.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 时空过程产品
 * </p>
 */
@Data
@TableName("product")
public class Product implements Serializable{

    @TableId(value = "pID", type = IdType.AUTO)
    private Integer pID;

    @TableField("pName")
    private String pName;

    @TableField("date")
    private String date;

    @TableField("path")
    private String path;

    @TableField("application")
    private String application;

    @TableField("modelUse")
    private String modelUse;
}
