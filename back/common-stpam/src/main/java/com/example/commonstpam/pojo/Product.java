package com.example.commonstpam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;//主键
    private String pname;//商品名称
    private Double pprice;//商品价格
    private Integer stock;//库存
}
