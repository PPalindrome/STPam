package com.example.server.controller.dataset;

import cn.hutool.core.io.FileUtil;
import com.example.server.pojo.RespBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


@RestController
@RequestMapping("/files")
public class DataOperateController {

    @Value("${server.port}")
    private String port;

    private static final String ip = "http://localhost";

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload/data")
    public RespBean upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();   //获取源文件名称
        String rootFilePath;
        rootFilePath = System.getProperty("user.dir") + "/server/src/main/resources/file/data" + originalFilename;
        FileUtil.writeBytes(file.getBytes(), rootFilePath);   //将文件写入到上传的路径
        return RespBean.success("数据集上传成功", ip + ":" + port + "/server/src/main/resources/file/data" + originalFilename);
    }

    /**
     * 删除文件
     *
     * @param originalFilename
     * @return
     */
    @PostMapping("/delete/data")
    public RespBean delete(@RequestBody String originalFilename) {
        //处理获取的源文件名
        String file = originalFilename.substring(0, originalFilename.length() - 1);
        String basePath, deletePath;
        List<String> fileNames;
        basePath = System.getProperty("user.dir") + "/server/src/main/resources/file/data";
        fileNames = FileUtil.listFileNames(basePath);
        //找到和源文件名一致的文件并删除
        for (String fileName : fileNames) {
            if (fileName.equals(file)) {
                deletePath = System.getProperty("user.dir") + "/server/src/main/resources/file/data" + fileName;
                FileSystemUtils.deleteRecursively(new File(deletePath));
                return RespBean.success("成功删除文件");
            }
        }
        return RespBean.error("删除文件失败，文件不存在");
    }
}
