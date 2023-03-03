package com.example.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.EncryptMapper;
import com.example.server.pojo.AdminLoginParam;
import com.example.server.service.IEncryptService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.*;
@Service
public class EncryptServiceImpl extends ServiceImpl<EncryptMapper, AdminLoginParam> implements IEncryptService {
    @Autowired
    IEncryptService IEncryptService;

    @Override
    public String genKeyPair() throws NoSuchAlgorithmException {
        final String ALGORITHM = "RSA";
        //秘钥长度
        final Integer KEY_LENGTH = 1024;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_LENGTH); // 秘钥字节数
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String publicStr = new String(Base64.encodeBase64(publicKey.getEncoded()));
        String privateStr = new String(Base64.encodeBase64(privateKey.getEncoded()));
        insertPrivateKey(privateStr,new AdminLoginParam());
        return publicStr;
    }

    @Override
    public void insertPrivateKey(String privateKey ,AdminLoginParam adminLoginParam) {
        adminLoginParam.setId(1);
        adminLoginParam.setPrivateKey(privateKey);
        IEncryptService.updateById(adminLoginParam);
    }


    @Override
    public String selectPrivateKetById(int id) {
        QueryWrapper<AdminLoginParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("private_key").eq("id", "1");
        AdminLoginParam param = IEncryptService.getOne(queryWrapper);
        return param.getPrivateKey();
    }
}

