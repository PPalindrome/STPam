package com.example.server.service.impl;

import com.example.server.service.IFileReadService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Service
public class FileReadServiceImpl implements IFileReadService {

    private String content;
    @Override
    public String fileContent(String filePath) {
        content="";
        try {
            //通过传入File实例化Scanner类
//            Scanner sc = new Scanner(new File("src/main/resources/static/files/时空过程深度学习模型/GRU与LSTM/GRU.PY"));
            Scanner sc =new Scanner(new File(filePath));
            //按行读取test.txt文件内容
            while (sc.hasNextLine()) {
                content=content+sc.nextLine()+"\n";
//                System.out.println(sc.nextLine());
            }
            System.out.println("成功获取文件内容");
        } catch (FileNotFoundException e) {
            System.out.println("读取失败，错误信息为："+e);
        }
//        System.out.println("已经重置content内容为："+content);
        return content;
    }
}
