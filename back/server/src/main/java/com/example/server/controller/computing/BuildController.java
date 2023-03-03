package com.example.server.controller.computing;

import cn.hutool.core.io.FileUtil;
import com.example.server.pojo.ComputeParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
public class BuildController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String filePath= System.getProperty("user.dir") +"/server/src/main/resources/files/model/";
    private final List<String> fileNames= FileUtil.listFileNames(filePath);
    private String pythonPath="";

    String modelFileName="",datasetPath="",distributeArch="";

    /**
     * 获取数据集和模型文件，发送命令行给控制台，运行python文件
     * @throws
     **/
    @PostMapping("/build")
    public String build(@RequestBody ComputeParam param) throws  IOException, InterruptedException {
        try{
            modelFileName=param.getModelFileName();
            datasetPath=param.getDatasetPath();
            distributeArch=param.getDistributeArch();

            for (String fileName : fileNames) {
                if (fileName.equals(modelFileName)) {
                    if(fileName.endsWith(".zip")){
                        String parent=unzip(filePath+fileName,filePath);
                        final String zipDirecPy=filePath+parent;
                        final List<String> zipDirecFiles= FileUtil.listFileNames(zipDirecPy);
                        for(String pyName:zipDirecFiles){
                            if(pyName.endsWith(".py")){
                                pythonPath=zipDirecPy+pyName;
                                System.out.println();
                            }
                        }
                    }else if(fileName.endsWith(".py")){
                        pythonPath=filePath+fileName;
                    }else{
                        return "非zip或python文件，无法执行！！！";
                    }
                }
            }

            System.out.println("开始计算");
            Process proc = Runtime.getRuntime().exec("python -m paddle.distributed.launch --gpus=0,1 "+pythonPath);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(proc.getInputStream (), StandardCharsets.UTF_8));
            String line;
            String result="";
            while((line=in.readLine())!=null){
                result=result+line+"\n";
            }
            System.out.println("计算结束");
            in.close();
            proc.waitFor();
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            return "错误";
        }
    }

    /**
     * 解压zip文件
     * zipFile是压缩包路径
     * outDir是解压路径
     */
    public static String unzip(String zipFile, String outDir) throws Exception {
        System.out.println("开始解压文件："+zipFile);
        if (!FileUtil.exist(zipFile)) {
            throw new FileNotFoundException();
        }
        final ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry entry = null;
        String parentDirec=zipInputStream.getNextEntry().toString();
        while ((entry = zipInputStream.getNextEntry()) != null) {
            System.out.println(entry);
            if (!entry.isDirectory()) {
                final File file = new File(outDir, entry.getName());
                if (!file.exists()) {
                    final boolean mkdirs = file.getParentFile().mkdirs();
                }
                final FileOutputStream fileOutputStream = new FileOutputStream(file);
                final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                int len = -1;
                final byte[] bytes = new byte[1024];
                while ((len = zipInputStream.read(bytes)) != -1) {
                    bufferedOutputStream.write(bytes, 0, len);
                }
                bufferedOutputStream.close();
                fileOutputStream.close();
            }
            zipInputStream.closeEntry();
        }
        System.out.println("解压完成！！！！");
        zipInputStream.close();
        return parentDirec;
    }

//    //从文件夹下载文件，不是hdfs
//    @GetMapping("/download")
//    public void download(HttpServletResponse response){
//        try{
//            File file=new File(filePath+"download/"+"main.py");
//            log.info(file.getPath());
//            //获取文件名
//            String filename=file.getName();
//            //获取文件后缀名
//            String ext=filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
//            log.info("文件后缀名："+ext);
//            // 将文件写入输入流
//            FileInputStream fileInputStream = new FileInputStream(file);
//            InputStream fis = new BufferedInputStream(fileInputStream);
//            byte[] buffer = new byte[fis.available()];
//            fis.read(buffer);
//            fis.close();
//
//            // 清空response
//            response.reset();
//            // 设置response的Header
//            response.setCharacterEncoding("UTF-8");
//            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
//            //attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.mp3"
//            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
//            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
//            // 告知浏览器文件的大小
//            response.addHeader("Content-Length", "" + file.length());
//            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/octet-stream");
//            outputStream.write(buffer);
//            outputStream.flush();
//        }catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
}
