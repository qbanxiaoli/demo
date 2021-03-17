package com.util;

import com.properties.MinioProperties;
import io.minio.*;
import io.minio.messages.Bucket;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * @author Q版小李
 * @description
 * @create 2021/3/16 16:18
 */
@Component
@ConditionalOnProperty(value = {"minio.property"}, matchIfMissing = true)
public class MinioUtil {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static MinioClient minioClient;

    private static MinioProperties minioProperties;

    public MinioUtil(MinioClient minioClient, MinioProperties minioProperties) {
        MinioUtil.minioClient = minioClient;
        MinioUtil.minioProperties = minioProperties;
    }

    /**
     * 上传文件
     */
    @SneakyThrows
    public static String uploadFile(MultipartFile multipartFile) {
        // 判断存储桶是否存在，不存在则创建
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
        }
        // 新的文件名 = 存储桶文件名_时间戳.后缀名
        String fileName = minioProperties.getBucketName() + "_" +
                System.currentTimeMillis() + "_" + dateFormat.format(new Date()) + "_" + new Random().nextInt(1000) +
                "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        //开始上传
        minioClient.putObject(
                PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(fileName).stream(
                        multipartFile.getInputStream(), multipartFile.getSize(), -1)
                        .contentType(multipartFile.getContentType())
                        .build());
        return minioProperties.getBucketName() + "/" + fileName;
    }

    /**
     * 上传⽂件
     *
     * @param objectName ⽂件名称
     * @param stream     ⽂件流
     */
    @SneakyThrows
    public static String uploadFile(String objectName, InputStream stream) {
        // 判断存储桶是否存在，不存在则创建
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
        }
        // 新的文件名 = 存储桶文件名_时间戳.后缀名
        String fileName = minioProperties.getBucketName() + "_" +
                System.currentTimeMillis() + "_" + dateFormat.format(new Date()) + "_" + new Random().nextInt(1000) +
                "." + FilenameUtils.getExtension(objectName);
        //开始上传
        minioClient.putObject(PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(objectName).stream(stream, stream.available(), -1).contentType(FilenameUtils.getExtension(objectName)).build());
        return minioProperties.getBucketName() + "/" + fileName;
    }

    /**
     * 获取全部bucket
     */
    @SneakyThrows
    public static List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }

    /**
     * 根据bucketName获取信息
     */
    @SneakyThrows
    public static Optional<Bucket> getBucket() {
        return minioClient.listBuckets().stream().filter(b -> b.name().equals(minioProperties.getBucketName())).findFirst();
    }

    /**
     * 根据bucketName删除信息
     */
    @SneakyThrows
    public static void removeBucket() {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
    }

    /**
     * 获取⽂件外链
     *
     * @param storePath ⽂件路径
     * @param expires   过期时间 <=7
     * @return url
     */
    @SneakyThrows
    public static String getObjectURL(String storePath, Integer expires) {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(storePath.substring(storePath.lastIndexOf("/")))
                .expiry(expires)
                .build());
    }

    /**
     * 获取⽂件
     *
     * @param storePath ⽂件路径
     * @return ⼆进制流
     */
    @SneakyThrows
    public static InputStream downloadFile(String storePath) {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(storePath.substring(storePath.lastIndexOf("/")))
                .build());
    }

    /**
     * 获取⽂件信息
     *
     * @param storePath ⽂件路径
     */
    @SneakyThrows
    public static StatObjectResponse getObjectInfo(String storePath) {
        return minioClient.statObject(StatObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(storePath.substring(storePath.lastIndexOf("/")))
                .build());
    }

    /**
     * 删除⽂件
     *
     * @param storePath ⽂件路径
     */
    @SneakyThrows
    public static void deleteFile(String storePath) {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(storePath.substring(storePath.lastIndexOf("/")))
                .build());
    }

    // 获取文件服务器地址
    public static String getWebServerUrl() {
        return minioProperties.getWebServerUrl();
    }

}
