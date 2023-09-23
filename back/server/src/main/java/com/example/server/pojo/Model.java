package com.example.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 *
 * </p>
 *
 * @author PanHuiWen
 * @since 2022-04-07
 */
@Data
@TableName("model")
public class Model implements Serializable{

    @TableId(value = "mid", type = IdType.AUTO)
    private Integer mid;
    @TableField("filename")
    private String filename;
    @TableField("mname")
    private String mname;
    @TableField("version")
    private String version;
    @TableField("author")
    private String author;
    @TableField("date")
    private String date;
    @TableField("command")
    private String command;
    @TableField("outpath")
    private String outpath;
}
