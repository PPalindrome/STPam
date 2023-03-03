package com.wfback.controller.dubboService;

import com.example.algorithmdubboservice.service.PythonExecuteService;
import com.example.commonstpam.pojo.AlgorithmRunParam;
import com.example.commonstpam.pojo.RespBean;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;


public class DubboAlgorithmRunController {

    @DubboReference
    private PythonExecuteService pythonExecuteService;

    @PostMapping("/runDubboAlgorithm")
    public RespBean runDubboAlgorithm(@RequestBody AlgorithmRunParam params) {
        int i=0;
        // runtime输入参数命令行的字符串数组

        //模型名称
        String modelName = params.getModelName();
        //模型参数（字符串按空格切分）
        String algorithmParam=params.getParams();
        //创建总参数数组存放模型名称+模型参数
        String[] paramsInput = new String[algorithmParam.length()+1];
        //将模型名字放进总参数数组第一个 现在即 模型名称+[参数字符串]
        paramsInput[0] = modelName;
        //按空格切分模型参数字符串为字符串数组（即模型参数数组）
        String[] algorithmParamInput = algorithmParam.split(" ");
        // 遍历模型参数数据将其放入总参数数组
        for (i = 0;i<algorithmParamInput.length;i++)
        {
            paramsInput[i+1]=algorithmParamInput[i];
        }
        String res=pythonExecuteService.algorithmExecute(paramsInput);
        return RespBean.success(res);
    }
}
