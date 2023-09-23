package com.example.server.controller.dataset;

import cn.hutool.core.io.FileUtil;
import com.example.server.pojo.Dataset;
import com.example.server.pojo.RespBean;
import com.example.server.service.IDatasetService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/files")
public class DataOperateController {

    @Value("${server.port}")
    private String port;

    private static final String ip = "http://localhost";
    private static final String dicName="/files/data/";

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     */

    @Autowired
    IDatasetService iDatasetService;
    @PostMapping("/upload/dataset")
    public RespBean upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();   //获取源文件名称
        //System.out.println(System.getProperty("user.dir") + dicName + originalFilename);
        //判断数据文件是否已经存在
        String dictPath=System.getProperty("user.dir") + dicName;
        File dictPathFile=new File(dictPath);
        String [] fileList= dictPathFile.list(); //获取文件夹下所有文件\
        List list= Arrays.asList(fileList);
        if (list.contains(originalFilename))
            return RespBean.error(500,"数据已存在，请选择其它数据上传");
        else{
            //首先判断文件是否超出大小
            // 不能直接进行运算，会损失精度,因此先取得大小
            double fileSize = file.getSize();
            //运算转换单位（保留2位小数）
            double fileSizeMB = new BigDecimal(fileSize/1024/1024).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //转换单位为MB
            if(fileSizeMB>40480)
                return RespBean.error(501,"超出文件大小限制");
            //将数据信息插入到数据库中
            String fileName=originalFilename;
            String rootFilePath = dictPath + originalFilename;
            //设置Date格式
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String uploadDate = dateformat.format(new Date());
            String description= String.valueOf(fileSizeMB)+"MB";
            iDatasetService.saveOrUpdate(new Dataset(fileName,rootFilePath,uploadDate,description));
            FileUtil.writeBytes(file.getBytes(), rootFilePath);   //将文件写入到上传的路径
            return RespBean.success("数据集上传成功", ip + ":" + port + rootFilePath);

        }

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
        basePath = System.getProperty("user.dir") + "/file/data/";
        fileNames = FileUtil.listFileNames(basePath);
        //找到和源文件名一致的文件并删除
        for (String fileName : fileNames) {
            if (fileName.equals(file)) {
                deletePath = System.getProperty("user.dir") + "/file/data/" + fileName;
                FileSystemUtils.deleteRecursively(new File(deletePath));
                return RespBean.success("成功删除文件");
            }
        }
        return RespBean.error("删除文件失败，文件不存在");
    }
}
