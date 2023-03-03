package com.wfback.controller.wpsService;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.wfback.pojo.RespBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


@RestController
@RequestMapping("/files")
public class WPSModelFilesController {
    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     */
    @Value("${server.port}")
    private String port;

    private static final String ip="http://localhost";

    @PostMapping("/upload")
    public RespBean upload(MultipartFile file) throws IOException {
        System.out.println("触发了一次！："+file.toString());
        String originalFilename = file.getOriginalFilename();   //获取源文件名称
        assert originalFilename != null;
        String fileFolder = originalFilename.substring(0, originalFilename.indexOf("."));
        System.out.println("上传的WPS原始文件名："+originalFilename);
        System.out.println("上传的WPS文件名："+fileFolder);
        String rootFilePath;
        //定义文件的唯一标识（前缀）
        //String flag = IdUtil.fastSimpleUUID();

        rootFilePath= System.getProperty("user.dir")+"/WF-Back/src/main/resources/files/uploadToWPSModel/"+fileFolder+"/"+originalFilename;  //获取上传路径
        FileUtil.writeBytes(file.getBytes(),rootFilePath);   //将文件写入到上传的路径
        return RespBean.success("模型上传成功",ip+":"+port+"/files/"+originalFilename);   //返回结果url
    }

    /**
     * 删除文件
     * @param originalFilename
     * @return
     */
    @PostMapping("/delete")
    public RespBean delete(@RequestBody String originalFilename){
        //处理获取的源文件名
//        String file=originalFilename.substring(0,originalFilename.length()-1);
        String file=originalFilename;

        String[]  names=file.split("/");
        for(int i=0,len=names.length;i<len;i++){
            System.out.println(names[i].toString());
        }
        file=names[names.length-1];

        System.out.println("文件名："+file);
        String basePath,deletePath;
        List<String> fileNames;
        String fileFolder = file.substring(0, file.indexOf("."));


        basePath=System.getProperty("user.dir")+"/WF-Back/src/main/resources/files/uploadToWPSModel/"+fileFolder+"/";
        fileNames= FileUtil.listFileNames(basePath);
        System.out.println("基本路径："+basePath);
        System.out.println("找到的文件："+fileNames.toString());
        //找到和源文件名一致的文件并删除
        for(String fileName:fileNames){
            if(fileName.equals(file)){
                deletePath= basePath+fileName;
                System.out.println("删除的文件："+deletePath);
                FileSystemUtils.deleteRecursively(new File(deletePath));
                return RespBean.success("成功删除文件");
            }
        }
        return RespBean.error("删除文件失败，文件不存在");
    }

    @GetMapping("/{flag}")
    public void getFile(@PathVariable String flag, HttpServletResponse response){
        OutputStream os; // 新建一个输出流
        String fileFolder = flag.substring(0, flag.indexOf("."));
        String basePath=System.getProperty("user.dir")+"/WF-Back/src/main/resources/files/uploadToWPSModel/"+fileFolder+"/";
        List<String> fileNames=FileUtil.listFileNames(basePath);  // 获取所有文件名称
        String fileName =fileNames.stream().filter(name->name.contains(flag)).findAny().orElse("");
        try{
            if (StrUtil.isNotEmpty(fileName)){
                response.addHeader("Content-Disposition","attachment;filename"+ URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes=FileUtil.readBytes(basePath+fileName);
                os=response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("文件下载失败，不支持的编码异常");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("文件下载失败，IO异常");
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/uploadDataset/{datasetModelName}")
    public RespBean getDatasetFile(@PathVariable String datasetModelName, MultipartFile file) throws IOException {
        String folderName=datasetModelName;
        System.out.println(folderName);

        String originalFilename = file.getOriginalFilename();   //获取源文件名称
        assert originalFilename != null;

        String rootFilePath;
//        //定义文件的唯一标识（前缀）
//        //String flag = IdUtil.fastSimpleUUID();
//
        rootFilePath= System.getProperty("user.dir")+"/WF-Back/src/main/resources/files/uploadToWPSModel/"+folderName+"/"+originalFilename;  //获取上传路径
        FileUtil.writeBytes(file.getBytes(),rootFilePath);   //将文件写入到上传的路径
        return RespBean.success("模型上传成功",ip+":"+port+"/files/uploadDataset/"+originalFilename);   //返回结果url

    }

}
