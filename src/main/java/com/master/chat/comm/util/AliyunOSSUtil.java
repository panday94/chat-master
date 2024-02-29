package com.master.chat.comm.util;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.master.chat.framework.properties.AliProperties;
import com.master.chat.common.api.FileInfo;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.exception.UpdateFailedException;
import com.master.chat.common.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 阿里云oss文件上传工具类
 * 涉及图片处理文档 https://help.aliyun.com/document_detail/47505.html?spm=a2c4g.11186623.6.1002.7d893b68EaiZK8
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Component
public class AliyunOSSUtil {
    @Autowired
    private AliProperties aliProperties;

    /**
     * 阿里云API的外网域名
     */
    private static final String ENDPOINT;

    /**
     * 阿里云API的bucket名称
     */
    public static final String BUCKET_NAME;

    /**
     * 阿里云API的密钥Access Key ID
     */
    private static String ACCESS_KEY_ID;

    /**
     * 阿里云API的密钥Access Key Secret
     */
    private static String ACCESS_KEY_SECRET;

    /**
     * 查看指定大小的云图片
     * logo图标
     */
    public static final String STYLE_LOGO = "?x-oss-process=style/logo";

    /**
     * 查看指定大小的云图片
     * 作滚动图
     */
    public static final String STYLE_BIGLOGO = "?x-oss-process=style/biglogo";

    /**
     * 查看指定大小的云图片
     * 类别图
     */
    public static final String STYLE_CLASS = "?x-oss-process=style/class";

    /**
     * 静态块
     */
    static {
        //初始化ENDPOINT
        ENDPOINT = "oss-cn-shanghai.aliyuncs.com";
        //初始化BUCKET_NAME
        BUCKET_NAME = "master-public";
    }

    /**
     * 获取阿里云OSS客户端对象
     */
    private static OSS getOssClient() {
        // 创建ClientConfiguration实例，按照您的需要修改默认参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 关闭CNAME选项。 设置是否支持CNAME。CNAME用于将自定义域名绑定到目标Bucket。
        conf.setSupportCname(false);
        return new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET, conf);
    }

    /**
     * 上传多个文件
     *
     * @param pathName 上传到服务器上的文件路径
     * @param files    来自本地的文件或者文件流
     * @retrun 图片访问路径
     */
    public static List<FileInfo> uploadFiles(MultipartFile[] files, String pathName) {
        List<FileInfo> fileInfos = Arrays.stream(files).map(file -> {
            return uploadFile(file, pathName);
        }).collect(Collectors.toList());
        return fileInfos;
    }

    /**
     * 上传文件
     *
     * @param pathName 上传到服务器上的文件路径
     * @param file     文件
     * @return 图片访问路径
     */
    public static FileInfo uploadFile(MultipartFile file, String pathName) {
        try (InputStream inputStream = file.getInputStream();) {
            return uploadFileInputSteam(inputStream, file.getOriginalFilename(), pathName, file.getSize());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UpdateFailedException();
        }
    }

    /**
     * 上传文件
     *
     * @param file     来自本地的文件或者文件流
     * @param pathName 上传到服务器上的文件路径
     * @return 图片访问路径
     */
    public static FileInfo uploadFile(File file, String pathName) {
        try (InputStream inputStream = new FileInputStream(file);) {
            return uploadFileInputSteam(inputStream, file.getName(), pathName, file.length());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UpdateFailedException();
        }
    }

    /**
     * 上传在线图片
     *
     * @param pathName 上传到服务器上的文件路径
     * @param fileName 图片路径
     * @return 图片访问路径
     */
    public static FileInfo uploadFile(String pathName, String fileName) {
        try (InputStream inputStream = new URL(fileName).openStream();) {
            return uploadFileInputSteam(inputStream, fileName, pathName, 0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UpdateFailedException();
        }
    }

    /**
     * 上传文件流
     *
     * @param inputStream 文件流
     * @param fileName    文件名称
     * @param pathName    路径名
     * @param size        文件大小
     */
    public static FileInfo uploadFileInputSteam(InputStream inputStream, String fileName, String pathName, long size) {
        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg
        String objectName = getObjectName(pathName, fileName);
        // 创建OSSClient实例。
        OSS ossClient = getOssClient();
        // 上传文件流。
        try {
            //上传到OSS
            ossClient.putObject(BUCKET_NAME, objectName, inputStream);
            //返回文件在服务器上的全路径+名称
            return new FileInfo(fileName, getRealName(objectName), size);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UpdateFailedException();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }

    /**
     * 下载图片到本地
     *
     * @param objectName 文件名称
     * @param localFile  本地地址
     */
    public static void download(String objectName, String localFile) {
        // 创建OSSClient实例。
        OSS ossClient = getOssClient();
        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(BUCKET_NAME, objectName), new File(localFile));
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 获取一个随机的文件名
     *
     * @param pathName 路径名
     * @param fileName 文件名
     * @return 返回加uuid后的文件名
     */
    private static String getObjectName(String pathName, String fileName) {
        //获取文件名后缀
        String suffix = StringPoolConstant.DOT + fileName.substring(fileName.lastIndexOf(StringPoolConstant.DOT) + 1);
        //获取一个uuid 去掉-
        String uuid = UUID.randomUUID().toString().replace("-", "") + RandomUtil.randomString(8);
        //查一下是否带路径
        int cutPoint = pathName.lastIndexOf("/") + 1;
        String objectName = uuid;
        //如果存在路径
        if (cutPoint != 0) {
            //掐头 如果开头是/ 则去掉
            String head = pathName.indexOf("/") == 0 ? pathName.substring(1, cutPoint) : pathName.substring(0, cutPoint);
            //返回正确的带路径的图片名称
            objectName = head + uuid;
        }
        return objectName + suffix;
    }

    /**
     * 获取存储在服务器上的地址
     *
     * @param oranName 文件名
     * @return 文件URL
     */
    private static String getRealName(String objectName) {
        return StringPoolConstant.HTTPS + BUCKET_NAME + StringPoolConstant.DOT + ENDPOINT + StringPoolConstant.SLASH + objectName;
    }

}
