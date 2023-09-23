package com.example.server.controller.computing;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.mapper.ProductMapper;
import com.example.server.pojo.ComputeParam;
import com.example.server.pojo.Model;
import com.example.server.pojo.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class BuildController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String filePath= System.getProperty("user.dir") +"/files/model/";


    String modelFileName="",datasetPath="",distributeArch="",command="",outpath="";

    /**
     * 获取数据集和模型文件，发送命令行给控制台，运行python文件
     * @throws
     **/
    @PostMapping("/build")
    public String build(@RequestBody ComputeParam param) throws  IOException {
        try{
            modelFileName=param.getModelFileName();
            if(modelFileName.endsWith(".zip")||modelFileName.endsWith(".rar")){
                modelFileName=modelFileName.substring(0,modelFileName.length()-4);  //文件名是zip或rar格式
            }else{
                modelFileName=modelFileName.substring(0,modelFileName.length()-3);  //文件名是sh、py、7z格式
            }
            datasetPath=param.getDatasetPath();
            distributeArch=param.getDistributeArch();
            command=param.getCommand();
            outpath=param.getOutpath();
            /**
             * 获取要运行的py文件名，直接运行，不要上面三个字符串
             */
            System.out.println("开始计算");
            String pythonPath="cmd /c cd "+filePath+modelFileName+"\n"+command;
            System.out.println("计算命令是："+pythonPath);
            Process proc = Runtime.getRuntime().exec(pythonPath);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(proc.getInputStream (), StandardCharsets.UTF_8));
            String line;
            String result="";
            while((line=in.readLine())!=null){
                result=result+line+"\n";
            }
            System.out.println("--------计算结束，计算结果是---------");
            System.out.println(result);
            in.close();
            proc.waitFor();
            System.out.println("开始保存输出结果");
            saveResults(modelFileName,outpath);
            return "完成计算";
        }catch (Exception e) {
            e.printStackTrace();
            return "计算失败，查看控制台";
        }
    }

    /**
     * 遍历数据库，存储计算生成的结果
     */
    @Autowired
    private ProductMapper productMapper;
    private void saveResults(String modelUse,String path){
        String productPath=filePath+modelUse+path;
        List<String> fileNames=FileUtil.listFileNames(productPath);
        for (String fileName : fileNames) {
            if (fileName.endsWith(".png")||fileName.endsWith(".jpg")) {
                if(productMapper.selectOne(new QueryWrapper<Product>().eq("pName",fileName))!=null){
                    productMapper.delete(new QueryWrapper<Product>().eq("pName",fileName));

                }
                Product product=new Product();
                product.setPath(productPath+fileName);
                product.setPName(fileName);
                product.setModelUse(modelUse);
                productMapper.insert(product);
            }
        }
    }

}
