package com.example.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    public Dataset(String name,String path,String date,String description){
        this.name=name;
        this.path=path;
        this.date=date;
        this.description=description;

    }
}
