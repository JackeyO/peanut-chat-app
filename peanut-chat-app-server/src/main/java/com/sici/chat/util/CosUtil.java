package com.sici.chat.util;

import cn.hutool.extra.spring.SpringUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author jackey
 * @description cos util
 * @date 5/27/25 15:45
 */
public class CosUtil {
    private static COSClient cosClient;
    static {
        cosClient = SpringUtil.getBean(COSClient.class);
    }

    public static PutObjectResult putObject(String bucketName, String key, MultipartFile file) throws IOException {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file.getInputStream(), null);
        return cosClient.putObject(putObjectRequest);
    }
}
