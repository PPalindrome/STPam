package com.wfback.controller.dubboService;

import com.example.commonstpam.pojo.RespBean;
import com.example.commonstpam.service.IFileService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DubboFileController {

    @DubboReference
    private IFileService fileService;

    @PostMapping("/fileUpload")
    public RespBean fileUpload(MultipartFile file) throws IOException {
        return fileService.fileUpload(file);
    }

    @PostMapping("/fileDelete")
    public RespBean delete(@RequestBody String originalFilename) {
        return fileService.fileDelete(originalFilename);
    }

    @GetMapping("/{flag}")
    public void getFile(@PathVariable String flag, HttpServletResponse response) {
        fileService.getFile(flag, response);
    }

    @PostMapping("/uploadDataset/{datasetModelName}")
    public RespBean uploadDataset(String datasetModelName, MultipartFile file) throws IOException {
        return fileService.getDatasetFile(datasetModelName, file);
    }
}
