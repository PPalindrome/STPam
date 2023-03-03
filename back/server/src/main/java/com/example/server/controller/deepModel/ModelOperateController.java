package com.example.server.controller.deepModel;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.mapper.ModelMapper;
import com.example.server.pojo.Model;
import com.example.server.pojo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@RestController
@RequestMapping("/files")
public class ModelOperateController {

    @Autowired
    private ModelMapper modelMapper;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("/upload/model")
    public RespBean upload(MultipartFile file) throws IOException {
        if( file!=null) {
            String originalFilename = file.getOriginalFilename();   //获取源文件名称
            System.out.println(originalFilename);
            String rootFilePath;
            rootFilePath = System.getProperty("user.dir") + "/server/src/main/resources/files/model/";  //获取上传路径
            //解压zip
            if (originalFilename != null) {
                FileUtil.writeBytes(file.getBytes(), rootFilePath + originalFilename);   //将文件写入到上传的路径
                if (originalFilename.endsWith(".zip")) {
                    //解压zip
                    try {
                        /**
                         * 解压方法有问题
                         *  虑先写模型优化的几个按钮
                         * 明天试下单个python文件能不能显示
                         */

                        //指定需要解压缩的zip文件
                        ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Paths.get(rootFilePath + originalFilename)));
                        ZipEntry zipEntry;
                        byte[] byteArray;
                        int len;
                        //遍历zip文件中的所有项，并逐个解压到指定的目录中
                        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                            try (FileOutputStream fileOutputStream = new FileOutputStream(rootFilePath + zipEntry.getName())) {
                                byteArray = new byte[1024];
                                while ((len = zipInputStream.read(byteArray)) != -1) {
                                    fileOutputStream.write(byteArray, 0, len);
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        return RespBean.success("模型已解压上传成功", originalFilename);   //返回结果url
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        return RespBean.error("模型解压上传失败！！");
                    }
                }
                return RespBean.success("非解压模型上传成功", originalFilename);
            }
            return RespBean.error("传入文件名称为空");
        }
        return RespBean.error("传入文件为空");
    }


    /**
     * 删除文件
     * @param originalFilename
     * @return
     */
    @PostMapping("/delete/model")
    public RespBean delete(@RequestBody String originalFilename){
        //处理获取的源文件名
        String file=originalFilename.substring(0,originalFilename.length()-1);
        String basePath,deletePath;
        List<String> fileNames;
        basePath = "/mnt/data/stpam"+"/file/model/";
        fileNames= FileUtil.listFileNames(basePath);
        //找到和源文件名一致的文件并删除
        for(String fileName:fileNames){
            if(fileName.equals(file)){
                //删除文件
                deletePath= basePath+fileName;
                FileSystemUtils.deleteRecursively(new File(deletePath));
                //删除数据库记录
                modelMapper.delete(new QueryWrapper<Model>().eq("filename",fileName));
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
//        String basePath = System.getProperty("user.dir")+"/server/src/main/resources/files/";
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

    /**
     * 运行python文件
     */
//    private static final String PATH = "..\\python\\main.py";
//    @PostMapping("/doPython")
//    public void doPython() throws IOException, InterruptedException {
//        final ProcessBuilder processBuilder = new ProcessBuilder("python", PATH);
//        processBuilder.redirectErrorStream(true);
//        final Process process = processBuilder.start();
//        final BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        String s ;
//        while ((s = in.readLine()) != null) {
//            System.out.println(s);
//        }
//        final int exitCode = process.waitFor();
//        System.out.println(exitCode == 0);
//    }
}
