package com.example.server.controller.deepModel;

import cn.hutool.json.JSONUtil;
import com.example.server.pojo.FileEntity;
import com.example.server.service.IDirectoryStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/directoryStructure")
public class DirectoryStructureController {



    @Autowired
    IDirectoryStructureService directoryStructureService;

    private final String dirPath = System.getProperty("user.dir") + "/files/model/";

    @PostMapping("/getFileDirectoryStructure")
    public Object getFileDirectoryStructure(@RequestBody String param) throws UnsupportedEncodingException {
        //param是1开头表示压缩包，找解压后的文件目录
        //param是0开头表示是单个非压缩文件，直接在dirPath中找
        //剪切传来的参数并转码，防止中文乱码和空格变加号
        param=param.substring(0,param.length()-1);
        URLDecoder.decode(param, "UTF-8");
        param = URLDecoder.decode(param, "UTF-8");
        System.out.println();
        String modelPath;

        if(param.endsWith(".zip")){
            param=param.substring(0,param.length()-4);
            System.out.println("是压缩包，去掉'.'之后的param是："+param);
            modelPath=dirPath+param;
            System.out.println(modelPath);
        }else{
            modelPath=dirPath+param;
        }
        List<String> fileList = directoryStructureService.getFileDirectoryStructure(modelPath,0);
        List<FileEntity> collect = getFilePathTree(fileList);
        System.out.println("-------转json输出结果-------");
        System.out.println(JSONUtil.toJsonStr(collect));
        return JSONUtil.toJsonStr(collect);


    }

    //生成目录结构
    public static List<FileEntity> getFilePathTree(List<String> paths) {
        Map<String, Integer> map = new LinkedHashMap<>();
        Integer id = 1;
        for (int i = 0; i < paths.size(); i++) {
            String[] path = paths.get(i).split("/");
            String p = "";
            for (int j = 0; j < path.length; j++) {
                p += path[j] + "/";
                if (!map.containsKey(p.substring(0, p.length() - 1))) {
                    map.put(p.substring(0, p.length() - 1), id++);
                }
            }
        }

        List<FileEntity> menus = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            FileEntity menu = new FileEntity();
            Integer values = entry.getValue();
            String[] keys = entry.getKey().split("/");
            menu.setId(values);
            if (keys.length == 1) {
                menu.setParentId(0);
                menu.setLabel(keys[0]);
                menu.setPath(keys[0]);
            } else {
                String path = "";
                for (int i = 0; i < keys.length - 1; i++) {
                    path += keys[i] + "/";
                }
                menu.setLabel(keys[keys.length - 1]);
                menu.setPath(String.join("/", keys));
                path = path.substring(0, path.length() - 1);
                menu.setParentId(map.get(path));
            }
            menus.add(menu);
        }
        //获取父节点
        List<FileEntity> collect = menus.stream().filter(m -> m.getParentId() == 0).map(
                (m) -> {
                    m.setChildren(getChildrens(m, menus));
                    return m;
                }
        ).collect(Collectors.toList());

        return collect;
    }

    /**
     * 递归查询子节点
     *
     * @param root 根节点
     * @param all  所有节点
     * @return 根节点信息
     */
    private static List<FileEntity> getChildrens(FileEntity root, List<FileEntity> all) {
        List<FileEntity> children = all.stream().filter(m -> {
            return Objects.equals(m.getParentId(), root.getId());
        }).map((m) -> {
                    m.setChildren(getChildrens(m, all));
                    return m;
                }
        ).collect(Collectors.toList());
        return children;
    }

}

