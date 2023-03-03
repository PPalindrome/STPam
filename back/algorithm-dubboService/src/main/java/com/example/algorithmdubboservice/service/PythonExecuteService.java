package com.example.algorithmdubboservice.service;

import com.example.algorithmdubboservice.service.impl.FileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@DubboService
public class PythonExecuteService {

    private FileServiceImpl fileServiceImpl;

    private String output;


    String pythonFilePath = System.getProperty("user.dir") + "/algorithm-dubboService/src/main/resources/files/algorithm/";

    public String algorithmExecute(String[] paramsInput) {

        int i;

        String[] inputArray = new String[paramsInput.length+2];

        // 设置runtime需要传入的参数
        inputArray[0] = "python";
        inputArray[1]=pythonFilePath;
        for (i=0;i<paramsInput.length;i++){
            inputArray[i+2]=paramsInput[i];
        }

        //Java数据a,b传入Python
        Process pr;
        try {
            pr = Runtime.getRuntime().exec(inputArray);
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(), "GBK"));
            String line;
            List<String> lines = new ArrayList<String>();
            while ((line = in.readLine()) != null) {
                System.out.println("!!!python文件有输出值:" + line);
                lines.add(line); //把Python的print值保存了下来
            }
            output = String.valueOf(lines);
            in.close();
            pr.waitFor();
            log.info("算法执行成功！结果为："+output);
            return "算法执行成功！结果为："+output;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            log.info("算法执行失败！");
            return "算法执行失败！请到控制台查看错误信息！";
        }

    }

}

