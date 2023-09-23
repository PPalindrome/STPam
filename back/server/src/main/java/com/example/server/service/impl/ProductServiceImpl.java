package com.example.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.ModelMapper;
import com.example.server.mapper.ProductMapper;
import com.example.server.pojo.Model;
import com.example.server.pojo.Product;
import com.example.server.service.IModelService;
import com.example.server.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Override
    public byte[] image2bytes(String imgSrc) throws Exception {
        FileInputStream fin = new FileInputStream(imgSrc);
        //可能溢出,简单起见就不考虑太多,如果太大就要另外想办法，比如一次传入固定长度byte[]
        byte[] bytes  = new byte[fin.available()];
        //将文件内容写入字节数组，提供测试的case
        fin.read(bytes);
        fin.close();
        return bytes;
    }

}
