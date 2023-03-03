package com.example.server.controller.computing;

import cn.hutool.core.io.FileUtil;
import com.example.server.pojo.RespBean;
//import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * 这是一个错误的java调用python方法
 * 只是因为模型python用的3.x，而jython2.7只适用于python2.x
 * 但我不想浪费学了半天的，所以留下了
 * pom里要加入hutool和jython才能运行
 */


@RestController
@RequestMapping("/xxxxxxxbuild")
public class BuildControllerFailed {
//    private final String filePath=System.getProperty("user.dir")+"/server/src/main/resources/files/models/";
//    private final List<String> fileNames= FileUtil.listFileNames(filePath);
//    private String pythonPath="";
//    BufferedReader bufferedReader = null;
//    /**
//     * 获取数据集和模型文件，读取sh脚本，发送命令行给控制台，运行python文件
//     * @throws
//     **/
//    @PostMapping("")
//    public RespBean build() throws  IOException, InterruptedException {
//        //用sh脚本
//        try {
//            for (String fileName : fileNames) {
//                if (fileName.endsWith(".sh")) {
//                    shellPath = filePath + fileName;
//                }
//            }
//            ProcessBuilder builder = new ProcessBuilder(".sh", shellPath);
//            // 错误流重定向到标准输出流
//            builder.redirectErrorStream(true);
//            Process ps = builder.start();
//            int exitValue = ps.waitFor();
//            if (0 != exitValue) {
//                return RespBean.error("读取脚本文件失败，错误码是:" + exitValue);
//            }
//            bufferedReader = new BufferedReader(new InputStreamReader(ps.getInputStream()));
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println("line = " + line);
//            }
//            return RespBean.success("成功读取脚本文件");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return RespBean.error("读取脚本失败，在控制台查看具体信息");
//        }
//
//        //使用python语句
//        try(PythonInterpreter interpreter = new PythonInterpreter()){
//            for (String fileName : fileNames) {
//                if (fileName.endsWith(".py")) {
//                    pythonPath = filePath + fileName;
//                }
//            }
//
//            //面向函数
//            //加载python程序
//            interpreter.execfile(pythonPath);
//            //调用python程序中的函数
//            PyFunction pyf=interpreter.get("power",PyFunction.class);
//            PyObject ddRes=interpreter._cal_(Py.newInteger(2),Py.newInteger(3));
//            System.out.println(ddRes);
//            interpreter.cleanup();
//
//            //面向对象
//            //python对象名
//            String pythonObjName="cal";
//            //python类名
//            String pythonClazzName="Calculator";
//            //加载python程序
//            interpreter.execfile(pythonPath);
//            //实例化python对象
//            interpreter.exec(pythonObjName+"="+pythonClazzName+"()");
//            //获取实例化的python对象
//            PyObject pyObject=interpreter.get(pythonObjName);
//            //调用python对象方法传递参数并接受返回值
//            PyObject result=pyObject.invoke("power",new PyObject[]{Py.newInteger(2),Py.newInteger(3)});
//            //计算结果转为双精度浮点型
//            double power=Py.py2double(result);
//            //控制台输出
//            System.out.println(power);
//            interpreter.cleanup();
//            return RespBean.success("执行成功，在控制台查看运行结果");
//        }catch (Exception e) {
//            e.printStackTrace();
//            return RespBean.error("读取文件失败，在控制台查看具体信息");
//        }
//    }
}
