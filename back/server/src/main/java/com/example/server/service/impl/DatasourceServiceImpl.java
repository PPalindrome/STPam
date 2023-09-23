package com.example.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csvreader.CsvWriter;
import com.example.server.controller.datasource.DBContextHolder;
import com.example.server.controller.datasource.DynamicDataSource;
import com.example.server.mapper.DatasourceMapper;
import com.example.server.pojo.Dataset;
import com.example.server.pojo.Datasource;
import com.example.server.service.IDatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class DatasourceServiceImpl extends ServiceImpl<DatasourceMapper, Datasource> implements IDatasourceService {
    private static final String dicName="/files/data/";
    @Autowired
    DatasourceMapper datasourceMapper;
    @Autowired
    private DynamicDataSource dynamicDataSource;


    @Override
    public List<Datasource> get() {
        return null;
    }

    @Override
    public void changeDb(Datasource datasource) throws Exception {

        //默认切换到主数据源,进行整体资源的查找
        DBContextHolder.clearDataSource();
        try{
            System.out.println("需要使用的的数据源已经找到,datasourceId是：" + datasource.getDatasourceID());
            //创建数据源连接&检查 若存在则不需重新创建
            if(dynamicDataSource.createDataSourceWithCheck(datasource)){
                //切换到该数据源
                DBContextHolder.setDataSource(datasource.getDatasourceID());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 插入新的数据源
     * @return
     */
    @Override
    public boolean insertDS(Datasource datasource){
        return datasourceMapper.insertDS(
                datasource.getDatasourceID(),datasource.getUrl(),datasource.getUsername(),datasource.getPassword(),
                datasource.getDatabasetype()
        );
    }

    public boolean createTables(){
        return datasourceMapper.createTables();
    }


    public List<HashMap<String,Object>> thansResultSet2List(ResultSet rs) throws SQLException {
        List<HashMap<String,Object>> result=new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> map=new HashMap<String,Object>();
        if(rs==null)return null;

        ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等
        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数
        try {
            while(rs.next()){
                map=new HashMap<String,Object>(columnCount);
                for(int i = 1; i <= columnCount; i++){
                    map.put(md.getColumnName(i), String.valueOf(rs.getObject(i)));
                }
                result.add(map);

            }
            System.out.println("result:"+result.toString());
            return result;
        } catch (SQLException e) {
            return null;
        }

    }
    /**
     *
     * @param defiled_info sql查询结果
     * @param fileName fileName为下载文件名,防止文件重名覆盖，可加入时间戳
     */
    public void defiled_csv(List<HashMap<String, Object>> defiled_info, String fileName) {
        String rootPath = "";
        String dictPath=System.getProperty("user.dir") + dicName;
        if(fileName == null){
            rootPath = dictPath+"file_" +System.currentTimeMillis()+".csv";
        }else {
            rootPath = dictPath+fileName+System.currentTimeMillis()+".csv";
        }
        //第一个参数为文件存储路径，第二个为指定导出的文件字段间的间隔符，第三个参数为转出编码
        CsvWriter csvWriter = new CsvWriter(rootPath, '|', Charset.forName("GBK"));
        //需要确保查询的结果不能为空，也可以在这做判断，不为空才能自动取表头，不然需要手动传入表头
        HashMap<String, Object> hashMap = defiled_info.get(0);
        Set<String> defiled_sets = hashMap.keySet();
        try {
            if (defiled_sets.size() > 0) {
                for (String key : defiled_sets) {
                    //写入表头信息
                    csvWriter.write(key, false);
                }
                //换行
                csvWriter.endRecord();
            }
            //写入类容信息
            for (int k = 0; k < defiled_info.size(); k++) {
                HashMap<String, Object> infos = defiled_info.get(k);
                for (String key : defiled_sets) {
                    //如果某字段为空，这转义为字符串"null"
                    if(infos.get(key) == null){
                        csvWriter.write("null");
                    }else {
                        csvWriter.write(infos.get(key).toString());
                    }
                }
                csvWriter.endRecord();
            }
            //关闭流
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Datasource findByDatasourceID(String datasourceID){
        QueryWrapper<Datasource> queryWrapper = new QueryWrapper<>();
        if(datasourceID!=null)
            queryWrapper.eq("datasourceID",datasourceID);
        return datasourceMapper.selectOne(queryWrapper);

    }

}
