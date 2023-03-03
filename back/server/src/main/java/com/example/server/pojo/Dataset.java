package com.example.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author PanHuiWen
 * @since 2022-09-07
 */
@Data
@TableName("dataset")
public class Dataset implements Serializable{

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("name")
    private String name;
    @TableField("path")
    private String path;
    @TableField("date")
    private String date;
    @TableField("description")
    private String description;
}
