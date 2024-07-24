package com.cupk.service.serviceImpl;

import com.cupk.service.CosService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class CosServiceImpl implements CosService {
    @Value("${cos.accessKey}")
    private String secretId;

    @Value("${cos.secretKey}")
    private String secretKey;

    @Value("${cos.regionName}")
    private String region;

    @Value("${cos.bucketName}")
    private String bucketName;

    @Value("${cos.baseUrl}")
    private String baseUrl;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosClient = new COSClient(cred, clientConfig);

        String key = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        File localFile = convertMultiPartToFile(file);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        cosClient.putObject(putObjectRequest);

        cosClient.shutdown();
        // 删除本地临时文件
        if (!localFile.delete()) {
            System.err.println("Failed to delete temporary file: " + localFile.getAbsolutePath());
        }
        return baseUrl + "/" + key;
    }

    @Override
    public File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = File.createTempFile("temp", null);
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }
}
