package com.master.chat.framework.util;

import cn.hutool.core.io.FileUtil;
import com.master.chat.common.config.dto.ExtraInfoDTO;
import com.master.chat.common.exception.UpdateFailedException;
import com.master.chat.framework.util.file.FileInfo;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 腾讯云cos文件上传工具类
 * 涉及图片处理文档 https://help.aliyun.com/document_detail/47505.html?spm=a2c4g.11186623.6.1002.7d893b68EaiZK8
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Slf4j
public class TencentCOSUtil {

    /**
     * 域名
     */
    private static String DOMAIN;

    /**
     * 地域节点
     */
    private static String REGION;

    /**
     * 阿里云API的bucket名称
     */
    public static String BUCKET_NAME;

    /**
     * secretId
     */
    private static String SECREID;

    /**
     * secretKey
     */
    private static String SECREKEY;

    /**
     * oss信息
     */
    private static ExtraInfoDTO OSS_INFO;

    /**
     * 图片策略
     */
    private static String styleRule;

    /**
     * 缩略图策略
     */
    private static String thumbnailStyleRule = "!Photo_Compression";

    /**
     * 文件类型
     */
    private static List<String> fileTypes = null;


    private static COSClient createCOSClient() {
        DOMAIN = String.format("https://%s.cos.ap-shanghai.myqcloud.com/", OSS_INFO.getBucketName());
        REGION = OSS_INFO.getEndpoint();
        BUCKET_NAME = OSS_INFO.getBucketName();
        SECREID = OSS_INFO.getOssKeyId();
        SECREKEY = OSS_INFO.getOssKeySecret();
        // 设置用户身份信息。
        // SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        COSCredentials cred = new BasicCOSCredentials(SECREID, SECREKEY);

        // ClientConfig 中包含了后续请求 COS 的客户端设置：
        ClientConfig clientConfig = new ClientConfig();

        // 设置 bucket 的地域
        // COS_REGION 请参照 https://cloud.tencent.com/document/product/436/6224
        clientConfig.setRegion(new Region(REGION));

        // 设置请求协议, http 或者 https
        // 5.6.53 及更低的版本，建议设置使用 https 协议
        // 5.6.54 及更高版本，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);

        // 以下的设置，是可选的：

        // 设置 socket 读取超时，默认 30s
        clientConfig.setSocketTimeout(30 * 1000);
        // 设置建立连接超时，默认 30s
        clientConfig.setConnectionTimeout(30 * 1000);

        // 如果需要的话，设置 http 代理，ip 以及 port
//        clientConfig.setHttpProxyIp("httpProxyIp");
//        clientConfig.setHttpProxyPort(80);

        return new COSClient(cred, clientConfig);
    }

    // 创建 TransferManager 实例，这个实例用来后续调用高级接口
    private static TransferManager createTransferManager() {
        // 创建一个 COSClient 实例，这是访问 COS 服务的基础实例。
        // 详细代码参见本页: 简单操作 -> 创建 COSClient
        COSClient cosClient = createCOSClient();

        // 自定义线程池大小，建议在客户端与 COS 网络充足（例如使用腾讯云的 CVM，同地域上传 COS）的情况下，设置成16或32即可，可较充分的利用网络资源
        // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
        ExecutorService threadPool = Executors.newFixedThreadPool(32);

        // 传入一个 threadpool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosClient, threadPool);

        // 设置高级接口的配置项
        // 分块上传阈值和分块大小分别为 5MB 和 1MB
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
        transferManagerConfiguration.setMinimumUploadPartSize(1 * 1024 * 1024);
        transferManager.setConfiguration(transferManagerConfiguration);

        return transferManager;
    }

    private static void shutdownTransferManager(TransferManager transferManager) {
        // 指定参数为 true, 则同时会关闭 transferManager 内部的 COSClient 实例。
        // 指定参数为 false, 则不会关闭 transferManager 内部的 COSClient 实例。
        transferManager.shutdownNow(true);
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 图片访问路径
     */
    public static FileInfo upload(ExtraInfoDTO ossInfo, MultipartFile file) throws Exception {
        return upload(ossInfo, file, null);
    }

    /**
     * 上传文件
     *
     * @param filePath 上传到服务器上的文件路径
     * @param file     文件
     * @return 图片访问路径
     */
    public static FileInfo upload(ExtraInfoDTO ossInfo, MultipartFile file, String filePath) {
        OSS_INFO = ossInfo;
        String fileFullName = FileUtil.getName(file.getOriginalFilename());
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
            return upload(inputStream, fileFullName, filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传文件
     *
     * @param file     来自本地的文件或者文件流
     * @param pathName 上传到服务器上的文件路径
     * @return 图片访问路径
     */
    public static FileInfo upload(ExtraInfoDTO ossInfo, File file, String filePath) {
        OSS_INFO = ossInfo;
        try (InputStream inputStream = new FileInputStream(file);) {
            return upload(inputStream, file.getName(), filePath);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UpdateFailedException();
        }
    }

    public static FileInfo upload(InputStream inputStream, String fileFullName, String filePath) {
        TransferManager transferManager = createTransferManager();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 上传的流如果能够获取准确的流长度，则推荐一定填写 content-length
        // 如果确实没办法获取到，则下面这行可以省略，但同时高级接口也没办法使用分块上传了
        //objectMetadata.setContentLength(inputStreamLength);
        try {
            //时间戳
            String timestamp = String.valueOf(System.currentTimeMillis());
            //文件扩展名
            String extension = FileUtil.getSuffix(fileFullName);
            String fileName = FileUtil.getPrefix(fileFullName);
            String upFilePath = StringUtils.join(fileName, "_", timestamp, ".", extension);
            if (filePath != null) {
                upFilePath = StringUtils.join(filePath, "/", upFilePath);
            }
            String key = upFilePath;
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, inputStream, objectMetadata);
            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            if (uploadResult == null) {
                log.error("上传附件到腾讯云失败 fileName={}", upFilePath);
                throw new Exception("上传附件 " + upFilePath + " 到腾讯云失败 ");
            }
            FileInfo uploadDto = new FileInfo();
            uploadDto.setFileName(fileFullName);
            uploadDto.setFilePath(DOMAIN + upFilePath);
            uploadDto.setFileUrl(DOMAIN + upFilePath);
            return uploadDto;
        } catch (Exception e) {
            log.error("cos 上传失败", e);
            throw new RuntimeException("文件=" + fileFullName + " 上传失败");
        } finally {
            shutdownTransferManager(transferManager);
        }
    }

    public static byte[] download(String key) throws Exception {
        return null;
    }

    public static void delete(String key) {

    }

}
