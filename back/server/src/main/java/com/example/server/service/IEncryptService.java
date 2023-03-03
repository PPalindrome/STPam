package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.AdminLoginParam;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
@Service
public interface IEncryptService extends IService<AdminLoginParam> {
    /**
     * 生成秘钥对，并将私钥插入到数据库中
     * @return 返回生成的公钥
     * @throws NoSuchAlgorithmException
     */
    String genKeyPair() throws NoSuchAlgorithmException;

    /**
     * 将私钥插入到数据库中
     * @param privateKey
     * @param adminLoginParam
     */
    void insertPrivateKey(String privateKey,AdminLoginParam adminLoginParam);

    /**
     * 根据id查询私钥
     * @param id
     * @return
     */
    String selectPrivateKetById(int id);
}

