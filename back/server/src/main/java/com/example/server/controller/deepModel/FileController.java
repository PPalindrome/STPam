package com.example.server.controller.deepModel;

import cn.hutool.core.io.FileUtil;
import com.example.server.pojo.FileEntity;
import com.example.server.service.IFileReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URLDecoder;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/fileEdit")
public class FileController {

    @Autowired
    IFileReadService iFileReadService;

    private String baseDir = System.getProperty("user.dir")+"/files/model/";

    private List<String> fileNames;

    private String contentPath = "";

    private String content;
    private String contentDir;

    public FileController() {
    }

    @PostMapping("/getCode")
    public String getContent(@RequestBody String filePath) throws UnsupportedEncodingException {
        filePath=filePath.substring(0,filePath.length()-1);
        URLDecoder.decode(filePath, "UTF-8");
        filePath = URLDecoder.decode(filePath, "UTF-8");
        String fileName="";
        int i=filePath.length()-1;
        for(;i>=0;i--){
            if(filePath.charAt(i)!='/'){
                fileName=filePath.charAt(i)+fileName;
            }else{
                break;
            }
        }
        contentDir=baseDir+filePath.substring(0,i+1);

        fileNames=FileUtil.listFileNames(contentDir);
        for (String name : fileNames) {
            if (name.equals(fileName)) {
                contentPath = contentDir + name;
            }
        }
        if (!Objects.equals(contentPath, ""))
        {
            content=iFileReadService.fileContent(contentPath);
        }
        else {
            System.out.println("未读取到任何有用的信息！");
        }
        return content;
    }

    @PostMapping("/saveCode")
    //指定接收数据的格式
    public boolean saveFileCode(@RequestBody FileEntity file){
        String saveFile = baseDir + file.getPath();
        System.out.println("文件路径：" + saveFile);

        File f = new File(saveFile);//新建一个文件对象，如果不存在则创建一个该文件
        try {

            FileWriter fw = new FileWriter(f);

            fw.write(file.getContent());//将字符串写入到指定的路径下的文件中

            fw.close();
            System.out.println("成功修改文件");
            return true;

        } catch (FileNotFoundException e) {
            System.out.println("file save failed!");
            e.printStackTrace();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;

    }

}