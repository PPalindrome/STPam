package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.Datasource;
import com.example.server.pojo.Model;
import com.example.server.pojo.RespBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author PanHuiWen
 * @since 2022-05-06
 */
@Mapper
public interface DatasourceMapper extends BaseMapper<Datasource> {
    /**
     * 根据数据源id切换数据源
     * @return
     */
    @Select("SELECT * FROM datasource")
    List<Datasource> get();

    /**
     * 插入新的数据源
     */
    @Insert("insert into datasource values(#{id},#{url},#{username},#{password},#{databasetype})")
    boolean insertDS(String id,String url,String username,String password,String databasetype);

//    /**
//     * 插入新的数据
//     */
//    @Insert("insert into datasource values(#{id},#{url},#{username},#{password},#{databasetype})")
//    boolean insert(String id,String url,String username,String password,String databasetype);

    /**
     * 新建数据库
     */
    @Update("create table model(mId int primary key,mName varchar(255),version varchar(255),application varchar(255))")
    boolean createTables();
}
