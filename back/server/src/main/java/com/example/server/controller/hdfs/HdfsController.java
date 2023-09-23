package com.example.server.controller.hdfs;

import com.example.server.pojo.RespBean;
import io.swagger.annotations.Api;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Api(tags = "HdfsController")
@RestController
@RequestMapping("/hdfs")
public class HdfsController {
    FileSystem fs;
    //创建操作hdfs的对象
    Configuration conf= new Configuration();

    private final String filePath=System.getProperty("user.dir")+"/files/model/";

    private void hdfsInit() throws IOException {
        conf.set("fs.defaultFS","hdfs://45.63.124.81:9000");
        fs=FileSystem.get(conf);
    }

    //创建hdfs客户端文件夹
    @PostMapping("/file")
    public RespBean init(@RequestBody String fileName) throws IOException {
        hdfsInit();
        String name=subFileName(fileName);
        try{
            //设置用户
//            System.setProperty("HADOOP_USER_NAME","gisdev2");
            fs.mkdirs(new Path(name));
            return RespBean.success("创建hdfs文件夹成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("创建失败！！");
        }
    }


    //hdfs文件上传
    @PostMapping("/put")
    public RespBean Put(@RequestBody String fileName) throws IOException {
        hdfsInit();
        String name=subFileName(fileName);
        //源、本地文件
        Path src=new Path(filePath+name);
        // pathFrom
        //目的地，hdfs路径
        Path dst=new Path("/user/gisdev1/");
        try{
            fs.copyFromLocalFile(src,dst);
            return RespBean.success("已上传到hdfs");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("hdfs错误，上传失败！！");
        }
    }

    //hdfs文件下载
    @GetMapping("/download/{path}")
    public void download(@PathVariable("path") String path , HttpServletResponse response) throws IOException {
        hdfsInit();
//        String name=subFileName(fileName);
        System.out.println(path);
        fs.copyToLocalFile(new Path("/user/gisdev1/"+path), new Path(filePath+"download/"));
        File file = new File(filePath+"download/"+ path);
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            //文件是否存在
            if (file.exists()) {
                //设置响应
                response.setContentType("application/octet-stream;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                os = response.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(file));
                while(bis.read(buffer) != -1){
                    os.write(buffer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bis != null) {
                    bis.close();
                }
                if(os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //修剪文件名
    private String subFileName(String filename){
        int l=filename.length();
        return filename.substring(0,l-1);
    }
}
