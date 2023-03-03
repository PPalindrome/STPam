package com.example.commonstpam.service;

import com.example.commonstpam.pojo.RespBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IFileService {

    RespBean fileUpload(MultipartFile file) throws IOException;

    RespBean fileDelete(String originalFilename);

    void getFile(String flag, HttpServletResponse response);

    RespBean getDatasetFile(String datasetModelName, MultipartFile file) throws IOException;
}
