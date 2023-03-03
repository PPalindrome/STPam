package com.example.algorithmdubboservice;

import com.example.algorithmdubboservice.mapper.AlgorithmMapper;
import com.example.commonstpam.pojo.DubboAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AlgorithmDubboServiceApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    private AlgorithmMapper algorithmMapper;

    //获取user表所有数据
    @Test
    void getData() {
        List<DubboAlgorithm> algorithm = algorithmMapper.selectList(null);
        System.out.println(algorithm);
    }

    //增加数据手动设置主键ID
//    @Test
//    void insertData() {
//        User user = new User(6l,"张三",20,"1223@qq.com");
//         int result = userMapper.insert(user);
//        System.out.println("返回结果，1代表插入成功，0代表失败："+result);
//    }

    //增加数据不设置主键ID(由mybatis_plus自动生成主键。)
    //利用snowflake算法自动生成一个19位的Long型ID.
    @Test
    void insertDataOne() {
        DubboAlgorithm algorithm = new DubboAlgorithm();
        algorithm.setMid(40);
        algorithm.setMname("李四四");
        algorithm.setMdescribe("1221233@qq.com");
        int result1 = algorithmMapper.insert(algorithm);
        System.out.println("返回结果，1代表插入成功，0代表失败："+result1+algorithm.getMuploadtime());
    }

    //删除数据
    @Test
    void deleteData() {

    }
    //修改数据
    @Test
    void updateData() {
        DubboAlgorithm algorithm = new DubboAlgorithm();
        algorithm.setMid(40);
        algorithm.setMname("修改了数据");
        algorithm.setMdescribe("1231231@qq.com");
        int row = algorithmMapper.updateById(algorithm); //返回的数据是影响的行数
        System.out.println("返回的数据是影响的行数"+algorithm);
    }

}
