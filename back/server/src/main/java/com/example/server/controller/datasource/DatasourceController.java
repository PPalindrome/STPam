package com.example.server.controller.datasource;

import com.alibaba.druid.mock.MockDriver;
import com.alibaba.druid.pool.DruidDataSource;
import com.example.server.controller.dataset.dao.PageDTO;
import com.example.server.controller.datasource.dao.DownloadDTO;
import com.example.server.controller.datasource.dao.TableDTO;
import com.example.server.controller.datasource.dao.TablePageDTO;
import com.example.server.mapper.DatasourceMapper;
import com.example.server.pojo.Dataset;
import com.example.server.pojo.Datasource;
import com.example.server.pojo.RespBean;
import com.example.server.service.IDatasetService;
import com.example.server.service.impl.DatasetServiceImpl;
import com.example.server.service.impl.DatasourceServiceImpl;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(tags = "DatasourceController")
@RestController
@RequestMapping("/data")
@RunWith(SpringRunner.class)
public class DatasourceController {
    //    private Map<String,String> driverMap=new HashMap<String,String>(){{
//        put("mysql","com.mysql.cj.jdbc.Driver");
//        put("oracle","oracle.jdbc.driver.OracleDriver");
//    }};
    private Gson gson=new Gson();
    private int totalTable;
    private static final String dicName="/files/data/";
    @Autowired
    private DatasourceMapper datasourceMapper;
    @Autowired
    private DatasourceServiceImpl datasourceService;
    @Autowired
    private DatasetServiceImpl datasetService;
    private Statement stDatabase=null;
    private Statement stTable=null;
    private Statement stDownloadTable;
    private DruidDataSource druidDataSource=new DruidDataSource();
    private  Configuration conf=new Configuration();
    private String datasourceType=null;
    private String userName=null;
    @PostMapping("/setting")
    /**
     * 注册数据库配置信息
     * 切换至新注册的数据库
     * @return
     */
    public RespBean dataSetting(@RequestBody Datasource datasource) throws Exception {
        String id = datasource.getDatasourceID();
        String username = datasource.getUsername();
        String password = datasource.getPassword();
        String url = datasource.getUrl();
        datasourceType=datasource.getDatabasetype();
        userName=datasource.getUsername();
        druidDataSource=new DruidDataSource();
        if(!datasourceType.equals("HDFS")&&(id == null || username == null || password == null || url == null))
            return RespBean.error("缺少必要的配置信息！", "500");
        //判断数据库中是否已有该数据库连接
        List<Datasource> dataSourcesList = datasourceMapper.get();
        for (Datasource ds : dataSourcesList) {
            if (ds.getDatasourceID().equals(id)) {
                return RespBean.error("已经存在该数据源，请输入其他连接名！", "501");
            }
        }
        try {
            datasourceService.insertDS(datasource);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("数据源配置失败！！");
        }
        if(!datasourceType.equals("HDFS")) {
            if (datasourceType.equals("mysql")) {
                druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            } else if (datasourceType.equals("oracle")) {
                druidDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            } else if (datasourceType.equals("postgresql")) {
                druidDataSource.setDriverClassName("org.postgresql.Driver");
            }
            druidDataSource.setUrl(datasource.getUrl());
            druidDataSource.setUsername(datasource.getUsername());
            druidDataSource.setPassword(datasource.getPassword());
            //可选设置
            druidDataSource.setMaxActive(10000);
            druidDataSource.setInitialSize(50);
            druidDataSource.setMaxWait(100);
            druidDataSource.setBreakAfterAcquireFailure(true);
            druidDataSource.setConnectionErrorRetryAttempts(0);
        }
        else if(datasourceType.equals("HDFS")){
            String regex = "^([^?]+)\\?([^?]+)\\?(\\d+)\\?([^?]+)\\?([^?]+)\\?(\\d+)\\?(\\d+)\\?([^?]+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(url);
            System.out.println(matcher);
            if (matcher.matches()) {
                String firstPart = matcher.group(1);  // hdfs://localhost:9000
                String secondPart = matcher.group(2); // postgres/hello
                String thirdPart = matcher.group(3); // 3
                String fourthPart = matcher.group(4);  // 128MB
                String fifthPart = matcher.group(5); // true
                String sixthPart = matcher.group(6); // 10
                String seventhPart = matcher.group(7); // 3
                String eighthPart = matcher.group(8); // false
                conf.set("fs.defaultFS", firstPart);
                conf.set("dfs.replication",thirdPart);
                conf.set("dfs.blocksize",fourthPart);
                conf.set("dfs.permissions.enabled",fifthPart);
                conf.set("dfs.namenode.handler.count",sixthPart);
                conf.set("dfs.client.block.write.retries",seventhPart);
                conf.set("dfs.client.use.datanode.hostname",eighthPart);
            }
        }
        return RespBean.success("数据源配置成功");}

    @PostMapping("/querryPage")
    @CrossOrigin
    public  String dataQuerry(@RequestBody TablePageDTO pageDTO) throws SQLException, IOException {
        List<TableDTO> result=new ArrayList<>();
        int currentPage= pageDTO.getCurrentPage();
        int pageSize=pageDTO.getPageSize();
        int offset = (currentPage - 1) * pageSize;
        if(!datasourceType.equals("HDFS")) {
            String searchData = pageDTO.getSearchData();
            String databaseName = pageDTO.getDatabaseName();
            String datasourceID = pageDTO.getDatasourceID();
            Connection connection = druidDataSource.getConnection();
            stDatabase = connection.createStatement();
            try {
                String sql = null;
                if (datasourceType.equals("mysql"))
                    sql = "SELECT TABLE_NAME,CONCAT(ROUND((DATA_LENGTH + INDEX_LENGTH) / (1024 * 1024), 2), ' MB') AS SIZE FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA =" + "'" + databaseName + "'" + " LIMIT ? OFFSET ?";
                else if (datasourceType.equals("oracle"))
                    sql = "SELECT table_name,CONCAT(LPAD(ROUND(bytes/1024/1024, 2), 4, '0'), ' MB') size_mb FROM ( SELECT table_name, num_rows, bytes, ROWNUM rn FROM ( SELECT t.table_name, t.num_rows, s.bytes FROM all_tables t, dba_segments s WHERE t.owner =" + "'" + userName + "'" + "AND t.table_name = s.segment_name AND s.owner =" + "'" + userName + "'" + "AND s.segment_type = 'TABLE') WHERE ROWNUM <=?) WHERE rn > ?";
                else if (datasourceType.equals("postgresql")) {
                    sql = "SELECT table_name, concat(round(pg_total_relation_size(table_schema || '.' || table_name)/1048576::numeric, 2), ' MB') AS table_size FROM information_schema.tables WHERE table_schema = 'public' LIMIT ? OFFSET ?";
                }
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
                    if (datasourceType.equals("oracle"))
                        preparedStatement.setInt(1, currentPage * pageSize);
                    if (datasourceType.equals("mysql") || datasourceType.equals("postgresql"))
                        preparedStatement.setInt(1, pageSize);
                    preparedStatement.setInt(2, offset);
                    try (ResultSet rs = preparedStatement.executeQuery()) {
                        // 处理查询结果
                        while (rs.next()) {
                            String tableName = rs.getString(1);
                            String size = rs.getString(2);
                            String date = new Date().toString();
                            TableDTO tableDTO = new TableDTO();
                            tableDTO.setName(tableName);
                            tableDTO.setSize(size);
                            tableDTO.setDate(date);
                            result.add(tableDTO);
                        }
                    } catch (Exception e) {
                        return "请配置数据库信息";
                        // 执行查询
                    }
                }
            } catch (Exception e) {
                return "数据库连接错误";
            }
            connection.close();
        }
        else if (datasourceType.equals("HDFS")){
            FileSystem fs = FileSystem.get(conf);
            int startIndex =offset; // 计算起始记录的下标
            String databaseName = pageDTO.getDatabaseName();
            //指定要查看的HDFS文件路径
            Path path = new Path(databaseName);
            // 获取文件状态
            FileStatus[] fileStatuses = fs.listStatus(path);
            int endIndex = Math.min(startIndex + pageSize, fileStatuses.length); // 计算结束记录的下标
            FileStatus[] pageFileStatuses = Arrays.copyOfRange(fileStatuses, startIndex, endIndex); // 获取指定页码的文件列表
            // 遍历文件状态数组，输出文件名称
            for (int i=0;i<pageFileStatuses.length;i++) {
                TableDTO tableDTO = new TableDTO();
                String fileName = pageFileStatuses[i].getPath().getName();
                long size=pageFileStatuses[i].getLen();
                double fileSizeInMB = (double) size / (1024 * 1024); // 将文件大小从字节转换为MB
                DecimalFormat df = new DecimalFormat("#.00"); // 创建 DecimalFormat 对象，保留两位小数
                String fileSizeFormatted = df.format(fileSizeInMB)+"MB"; // 将文件大小格式化为字符串
                String date = new Date().toString();
                tableDTO.setName(fileName);
                tableDTO.setSize(fileSizeFormatted);
                tableDTO.setDate(date);
                result.add(tableDTO);
            }
            // 关闭文件系统
            fs.close();
        }
        return gson.toJson(result);
    }
    @PostMapping("/querryTotal")
    public RespBean getDataTotal(@RequestBody TablePageDTO pageDTO) throws SQLException, IOException {
        String databaseName=pageDTO.getDatabaseName();
        if(datasourceType.equals("mysql")) {
            Connection connection = druidDataSource.getConnection();
            stDatabase = connection.createStatement();
            ResultSet resultSet = stDatabase.executeQuery("SELECT COUNT(*) FROM information_schema.TABLES WHERE table_schema =" + "'" + databaseName + "'");
            while (resultSet.next()) {
                totalTable = new Integer(resultSet.getString(1));
            }
            connection.close();
        }
        else if (datasourceType.equals("oracle") ){
            Connection connection = druidDataSource.getConnection();
            stDatabase = connection.createStatement();
            ResultSet resultSet = stDatabase.executeQuery("SELECT COUNT(*) FROM all_tables WHERE owner =" + "'" + userName + "'");
            while (resultSet.next()) {
                totalTable = new Integer(resultSet.getString(1));
            }
            connection.close();
        }
        else if (datasourceType.equals("postgresql") ){
            Connection connection = druidDataSource.getConnection();
            stDatabase = connection.createStatement();
            ResultSet resultSet = stDatabase.executeQuery(" SELECT count(*) AS table_count FROM information_schema.tables  WHERE table_schema = 'public'");
            while (resultSet.next()) {
                totalTable = new Integer(resultSet.getString(1));
            }
            connection.close();
        } else if (datasourceType.equals("HDFS") ) {
            FileSystem fs = FileSystem.get(conf);
            // 获取文件状态
            Path path = new Path(databaseName);
            FileStatus[] fileStatuses = fs.listStatus(path);
            totalTable=fileStatuses.length;
            fs.close();
        }
        return RespBean.success(totalTable);
    }
    @PostMapping("/download")
    public RespBean downloadData(@RequestBody DownloadDTO downloadDTO) throws SQLException, IOException {
        ResultSet resultSet=null;
        String dabaseName=downloadDTO.getDatabaseName();
        String tableName = downloadDTO.getTableName();
        if(datasourceType.equals("mysql")||datasourceType.equals("oracle")||datasourceType.equals("postgresql")) {
            Connection connection = druidDataSource.getConnection();
            // 普通对象
            Statement st = connection.createStatement();
            // 执行SQL语句
            resultSet = st.executeQuery("select * from " + tableName);
            List<HashMap<String, Object>> hashMaps = datasourceService.thansResultSet2List(resultSet);
            datasourceService.defiled_csv(hashMaps,tableName);
            String fileName=tableName+System.currentTimeMillis()+".csv";
            //设置Date格式
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String uploadDate = dateformat.format(new Date());
            String dictPath=System.getProperty("user.dir") + dicName;
            String rootPath = dictPath+fileName+System.currentTimeMillis()+".csv";
            datasetService.saveOrUpdate(new Dataset(fileName,rootPath,uploadDate, tableName));
        }
        else if (datasourceType.equals("HDFS")) {
            // 获取HDFS文件系统
            FileSystem fs = FileSystem.get(conf);
            // 指定要下载的HDFS文件路径
            Path hdfsPath = new Path(dabaseName+"/"+tableName);
            String dictPath=System.getProperty("user.dir") + dicName;
            Path localPath = new Path(dictPath);
            // 下载文件
            FileUtil.copy(fs, hdfsPath, fs.getLocal(conf), localPath, false, conf);
            // 关闭文件系统
            fs.close();
        }
        return RespBean.success("下载成功！");

    }
}
