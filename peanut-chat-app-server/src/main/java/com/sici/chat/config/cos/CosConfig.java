package com.sici.chat.config.cos;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author jackey
 * @description cos配置类
 * @date 5/27/25 15:08
 */
@Configuration
public class CosConfig {
    @Bean
    public COSClient cosClient() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        // TODO 现在代码里写死，不在配置文件里配置，后续使用配置文件 - Su Xiao Wen - 5/27/25 15:17

        String secretId = "AKIDb5MQGrENI0UlwviI7lBMK4arvkMjtsdB";
        String secretKey = "OkvviHclgOtAMTM1ReZIm24qSr21mJbg";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        // 2 设置 bucket 的地域, COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-shanghai");
        ClientConfig clientConfig = new ClientConfig(region);
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);

        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        return cosClient;
    }
}
