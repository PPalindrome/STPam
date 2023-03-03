package com.example.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.ModelMapper;
import com.example.server.pojo.Model;
import com.example.server.service.IModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author PanHuiWen
 * @since 2022-05-06
 */
@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model> implements IModelService {

    @Autowired
    ModelMapper modelMapper;



    //    @Autowired
//    private ModelMapper modelMapper;

//    /**
//     * 构建模型后上传到服务器
//     * @param request
//     * @return
//     */
//    @Override
//    public String upload(HttpServletRequest request) {
//        Process proc;
//        try {
//            proc = Runtime.getRuntime().exec("python F:\\stpam\\JSJProject\\jsj_batang2.py");// 执行py文件
//            //用输入输出流来截取结果
//            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
//            in.close();
//            proc.waitFor();
//            return "上传成功";
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            return "上传失败";
//        }
//
//    }
}
