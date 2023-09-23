package com.example.server.service.impl;

import com.example.server.service.IDirectoryStructureService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DirectoryStructureServiceImpl implements IDirectoryStructureService {

    private int fileLevel;

    private String filePath;

    private List<String> filePaths=new ArrayList<String>();

    private final String baseDirPath=System.getProperty("user.dir") + "/files/model/";

    public DirectoryStructureServiceImpl() {
    }

    /**
     * 生成输出格式
     * @param name 输出的文件名或目录名
     * @param level 输出的文件名或者目录名所在的层次
     * @return 输出的字符串
     */
    public String createPrintStr(String name, int level) {
        // 输出的前缀
        String printStr = "";
        // 按层次进行缩进
        for (int i = 0; i < level; i ++) {
            printStr  = printStr + "  ";
        }
        printStr = printStr + "- " + name;
        return printStr;
    }

    /**
     * 输出初始给定的目录
     * @param dirPath 给定的目录
     */
    public void printDir(String dirPath){
        // 将给定的目录进行分割
        String[] dirNameList = dirPath.split("\\\\");
        // 设定文件level的base
        fileLevel = dirNameList.length;
        // 按格式输出
        for (int i = 0; i < dirNameList.length; i ++) {
            System.out.println(createPrintStr(dirNameList[i], i));
        }
    }

    /**
     * 输出给定目录下的文件，包括子目录中的文件
     * @param dirPath 给定的目录
     * @param flag 遍历次数，为0表示第一次遍历
     */
    @Override
    public List<String> getFileDirectoryStructure(String dirPath,int flag) {
        if(flag==0){
            filePaths.clear();
        }
        // 建立当前目录中文件的File对象
        File curfile = new File(dirPath);
        System.out.println("curfile是："+curfile);
        // 取得代表目录中所有文件的File对象数组
        File[] list = curfile.listFiles();
        System.out.println("listcurfile是："+list);
        if(list==null){
            filePaths.add(dirPath);
            System.out.println("list为null，文件集合："+filePaths);
            return filePaths;
        }
        // 遍历file数组
        for (File file : list) {
            if (file.isDirectory()) {
                fileLevel++;
                // 递归子目录
                getFileDirectoryStructure(file.getPath(),flag++);
                fileLevel--;
            } else {
                filePath=file.getPath().replace(baseDirPath, "").replace("\\", "/");
                System.out.println("相对路径："+filePath);
                filePaths.add(filePath);
            }
        }
        System.out.println("文件集合："+filePaths);
        return filePaths;
    }

}
