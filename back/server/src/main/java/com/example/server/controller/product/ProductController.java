package com.example.server.controller.product;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.mapper.ProductMapper;
import com.example.server.pojo.Product;
import com.example.server.pojo.RespBean;
import com.example.server.service.IProductService;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private IProductService iProductService;

    private Gson gson=new Gson();
    /**
     * 获取产品列表
     */
    @PostMapping("/queryProduct")
    @CrossOrigin
    public String queryProduct() {
        try {
            List<Product> products=productMapper.selectList(null);
            return gson.toJson(products);
        }catch (Exception e){
            e.printStackTrace();
            return "没有查询到产品列表";
        }
    }

    /**
     * 获取图片，传到前端
     */
    @ApiOperation(value = "")
    @PostMapping("/getImgBytes")
    @ResponseBody
    public byte[] getImgBytes(@RequestBody JSONObject jsonObject) throws Exception {
        Object obj = jsonObject.get("imgStr");  //前端传来的是json对象，需要去除字符串
        String str = obj.toString(); //Object对象转String
        System.out.println(str);
        byte[] b = iProductService.image2bytes(str);
        System.out.println(b);
        b= Base64.encodeBase64(b);
        System.out.println(b);
        return b;
    }

}
