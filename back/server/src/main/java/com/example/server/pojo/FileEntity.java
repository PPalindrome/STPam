package com.example.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileEntity {
    /**
     * id
     */
    public Integer id;
    /**
     * 名称
     */
    public String label;
    /**
     * 路径
     */
    public String path;
    /**
     * 内容
     */
    public String content;
    /**
     * 父id ，根节点为0
     */
    public Integer parentId;
    /**
     * 子节点信息
     */
    public List<FileEntity> children;



}
