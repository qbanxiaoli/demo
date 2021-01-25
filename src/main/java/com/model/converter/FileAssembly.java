package com.model.converter;

import com.model.entity.SysFileInfo;
import com.util.FastDFSUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-01-09 10:23
 */
public class FileAssembly {

    public static SysFileInfo toDomain(String storePath, MultipartFile multipartFile) {
        return setFile(new SysFileInfo(), storePath, multipartFile);
    }

    public static SysFileInfo toDomain(SysFileInfo sysFileInfo, String storePath, MultipartFile multipartFile) {
        return setFile(sysFileInfo, storePath, multipartFile);
    }

    private static SysFileInfo setFile(SysFileInfo sysFileInfo, String storePath, MultipartFile multipartFile) {
        // 设置文件地址
        sysFileInfo.setStorePath(storePath);
        // 设置文件服务器访问地址
        sysFileInfo.setWebServerUrl(FastDFSUtil.getWebServerUrl());
        // 设置文件类型
        sysFileInfo.setContentType(multipartFile.getContentType());
        // 设置文件名
        sysFileInfo.setFileName(multipartFile.getOriginalFilename());
        // 设置文件扩展名
        sysFileInfo.setFileExtension(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        // 设置文件大小
        sysFileInfo.setFileSize(multipartFile.getSize());
        return sysFileInfo;
    }

}
