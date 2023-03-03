package com.wfback.controller.wpsService;

import com.wfback.pojo.WPSRunParam;
import net.opengis.wps.x100.ExecuteDocument;
import net.opengis.wps.x100.ProcessDescriptionType;
import org.n52.wps.client.ExecuteRequestBuilder;
import org.n52.wps.client.WPSClientException;
import org.n52.wps.client.WPSClientSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Arrays;

@RestController
public class AlgorithmServiceController {

    private String url = "http://localhost:8080/wps/WebProcessingService";

    private String processID = "org.n52.wps.server.algorithm.test.PythonAlgorithmService";

    @PostMapping("/runWPSModel")
    private String[] run(@RequestBody WPSRunParam param) throws IOException, WPSClientException, TransformerException {
        System.out.println("AlgorithmService Start！");

        // 连接会话
        WPSClientSession wpsClient = WPSClientSession.getInstance();
        boolean connected = wpsClient.connect(url);
        if ( connected) {
            System.out.println("WPS已连接！");
        }
        else {
            System.out.println("WPS连接失败！可能已经连接！");
//            return null;
        }

        // 看一下流程说明
        ProcessDescriptionType processDescription = wpsClient.getProcessDescription(url, processID);
        System.out.println("AlgorithmService process description:\n" + processDescription.xmlText() + "\n");

        String modelName=param.getModelName();
        String params=param.getParams();

        System.out.println("模型名称和参数为："+modelName+params);

        // 创建请求，添加文字输入
        ExecuteRequestBuilder executeBuilder = new ExecuteRequestBuilder(processDescription);
        String parameterIn = "literalInput";

        executeBuilder.addLiteralData(parameterIn, modelName);

        String [] paramsInput = params.split(" ");

        for(String input : paramsInput){
            executeBuilder.addLiteralData(parameterIn, input);
        }

        String parameterOut = "literalOutput";
        executeBuilder.setResponseDocument(parameterOut, null, null, null);

        if ( !executeBuilder.isExecuteValid()) {
            System.out.println("创建的执行请求无效。");
        }

        // 构建并发送请求文档
        ExecuteDocument executeRequest = executeBuilder.getExecute();
        System.out.println("Sending execute request:\n" + executeRequest.xmlText() + "\n");
        Object response = wpsClient.execute(url, executeRequest);
        System.out.println("Got response:\n" + response.toString() + "\n");

        // 断开连接
        wpsClient.disconnect(url);

        String request = executeRequest.toString().replaceAll(":ns", ":wps").replaceAll("ns:", "wps:").replaceAll ("xmlwps", "xmlns");

        String [] requestAndResponse={request,response.toString()};

        System.out.println(Arrays.toString(requestAndResponse));

        return requestAndResponse;
    }
}

