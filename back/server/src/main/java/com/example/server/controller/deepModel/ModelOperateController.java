package com.example.server.controller.deepModel;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.mapper.ModelMapper;
import com.example.server.pojo.Model;
import com.example.server.pojo.RespBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


@RestController
@RequestMapping("/files")
public class ModelOperateController {

    @Autowired
    private ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(ModelOperateController.class);
    String rootFilePath = System.getProperty("user.dir") + "/files/model/";  //获取上传路径
    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload/model")
    public RespBean upload(MultipartFile file) throws IOException {
        if (file != null) {
            String originalFilename = file.getOriginalFilename();   //获取源文件名称
            FileUtil.writeBytes(file.getBytes(),rootFilePath+originalFilename);   //将文件写入到上传的路径
            //解压zip
            if (originalFilename != null) {
                if (originalFilename.endsWith(".zip")) {
                    //解压zip
                    try {
                        unZip(new File(rootFilePath + originalFilename), rootFilePath);
                        delete(originalFilename);
                        return RespBean.success("模型已解压上传成功", originalFilename);   //返回结果url
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        return RespBean.error("模型解压上传失败！！");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                return RespBean.success("非解压模型上传成功", originalFilename);
            }
            return RespBean.error("传入文件名称为空");
        }
        return RespBean.error("传入文件为空");
    }

    /**
     * 解压模型文件夹
     * @param sourceFile
     * @param targetDir
     * @throws IOException
     */
    public static void unZip(File sourceFile, String targetDir) throws IOException {
        long start = System.currentTimeMillis();
        if (!sourceFile.exists()) {
            throw new FileNotFoundException("cannot find the file = " + sourceFile.getPath());
        }
//        ZipFile zipFile = new ZipFile(zipFile, Charset.forName("gbk"));
        ZipFile zipFile=null;
        try{
            zipFile = new ZipFile(sourceFile, Charset.forName("gbk"));
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.isDirectory()) {
                    String dirPath = targetDir + "/" + entry.getName();
                    createDirIfNotExist(dirPath);
                } else {
                    File targetFile = new File(targetDir + "/" + entry.getName());
                    createFileIfNotExist(targetFile);
                    InputStream is = null;
                    FileOutputStream fos = null;
                    try {
                        is = zipFile.getInputStream(entry);
                        fos = new FileOutputStream(targetFile);
                        int len;
                        byte[] buf = new byte[1024];
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                    }finally {
                        try{
                            fos.close();
                        }catch (Exception e){
                            log.warn("close FileOutputStream exception", e);
                        }
                        try{
                            is.close();
                        }catch (Exception e){
                            log.warn("close InputStream exception", e);
                        }
                    }
                }
            }
            log.info("解压完成，耗时：" + (System.currentTimeMillis() - start) +" ms");
        } finally {
            if(zipFile != null){
                try {
                    zipFile.close();
                } catch (IOException e) {
                    log.warn("close zipFile exception", e);
                }
            }
        }
    }

    public static void createDirIfNotExist(String path){
        File file = new File(path);
        createDirIfNotExist(file);
    }

    public static void createDirIfNotExist(File file){
        if(!file.exists()){
            file.mkdirs();
        }
    }

    public static void createFileIfNotExist(File file) throws IOException {
        createParentDirIfNotExist(file);
        file.createNewFile();
    }

    public static void createParentDirIfNotExist(File file){
        createDirIfNotExist(file.getParentFile());
    }


    /**
     * 删除文件或压缩包
     *
     * @param originalFilename
     * @return
     */
    public RespBean delete(String originalFilename) {
        //处理获取的源文件名
        String deletePath;
        List<String> fileNames;
        fileNames = FileUtil.listFileNames(rootFilePath);
        //找到和源文件名一致的文件并删除
        for (String fileName : fileNames) {
            if (fileName.equals(originalFilename)) {
                //删除文件
                deletePath = rootFilePath + fileName;
                FileSystemUtils.deleteRecursively(new File(deletePath));
                //删除数据库记录
                modelMapper.delete(new QueryWrapper<Model>().eq("filename", fileName));
                return RespBean.success("成功删除文件");
            }
        }
        return RespBean.error("删除文件失败，文件不存在");
    }


    /**
     * 下载已经上传的文件
     * @param response
     * @return
     */
//    @GetMapping("/{flag}")
//    public void getFiles(@PathVariable String flag, HttpServletResponse response){
//        //新建输出流对象
//        OutputStream os;
//        //定义文件上传的根路径
//        String basePath = System.getProperty("user.dir")+"/files/";
//        //获取根路径的所有文件名称
//        List<String> fileNames = FileUtil.listFileNames(basePath);
//        //找到和参数一致的文件
//        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
//        try {
//            if(StrUtil.isNotEmpty(fileName)){
//                response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
//                response.setContentType("application/octet-stream");
//                //通过文件路径读取文件字节流
//                byte[] bytes = FileUtil.readBytes(basePath + fileName);
//                //通过输出流返回文件
//                os=response.getOutputStream();
//                os.write(bytes);
//                os.flush();
//                os.close();
//            }
//        }catch (Exception e){
//            System.out.println("文件下载失败");
//        }
//    }

    //从文件夹下载文件，不是hdfs
    @GetMapping("/download")
    public void download(@RequestBody String fileName,HttpServletResponse response){
        try{
            File file=new File(rootFilePath+"download/"+fileName);
            log.info(file.getPath());
            //获取文件名
            String filename=file.getName();
            //获取文件后缀名
            String ext=filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
            log.info("文件后缀名："+ext);
            // 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

