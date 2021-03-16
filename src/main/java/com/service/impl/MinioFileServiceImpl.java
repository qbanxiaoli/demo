package com.service.impl;

import com.dao.mapper.SysFileInfoMapper;
import com.dao.repository.FileRepository;
import com.enums.ResponseEnum;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.model.converter.FileAssembly;
import com.model.dto.QueryDTO;
import com.model.dto.SysFileInfoFromDTO;
import com.model.entity.QSysFileInfo;
import com.model.entity.SysFileInfo;
import com.model.vo.PageVO;
import com.model.vo.SysFileInfoVO;
import com.service.FileService;
import com.util.*;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/10/19 9:57 PM
 */
@Slf4j
@Primary
@Service
@ConditionalOnProperty(value = {"minio.property"}, matchIfMissing = true)
public class MinioFileServiceImpl extends FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public MinioFileServiceImpl(SysFileInfoMapper sysFileInfoMapper, FileRepository fileRepository) {
        super(sysFileInfoMapper, fileRepository);
        this.fileRepository = fileRepository;
    }

    @Override
    public SysFileInfoVO uploadImage(MultipartFile multipartFile, SysFileInfoFromDTO sysFileInfoFromDTO) {
        if (sysFileInfoFromDTO.getThumbnail()) {
            // 压缩图片
            byte[] bytes = ImageUtil.compressImage(multipartFile, sysFileInfoFromDTO);
            // 上传压缩后的图片
            String storePath = MinioUtil.uploadFile(multipartFile.getOriginalFilename(), new ByteArrayInputStream(bytes));
            log.info("上传缩略图地址：{}", MinioUtil.getWebServerUrl() + storePath);
            // 上传缩略图
            return this.saveFile(MinioUtil.getWebServerUrl(), storePath, multipartFile, true);
        }
        log.info("上传图片大小：{}B", multipartFile.getSize());
        String storePath = MinioUtil.uploadFile(multipartFile);
        log.info("上传图片地址：{}", MinioUtil.getWebServerUrl() + storePath);
        // 上传原图
        return this.saveFile(storePath, MinioUtil.getWebServerUrl(), multipartFile, false);
    }

    @Override
    public SysFileInfoVO uploadFile(MultipartFile multipartFile) {
        // 上传文件
        String storePath = MinioUtil.uploadFile(multipartFile);
        log.info("上传文件地址：{}", MinioUtil.getWebServerUrl() + storePath);
        // 上传原文件
        return this.saveFile(MinioUtil.getWebServerUrl(), storePath, multipartFile, false);
    }

    @Override
    @SneakyThrows
    public void downloadFile(Long id) {
        SysFileInfo sysFileInfo = this.getFileById(id);
        log.info("文件访问地址：{}", sysFileInfo.getWebServerUrl() + sysFileInfo.getStorePath());
        InputStream inputStream = MinioUtil.downloadFile(sysFileInfo.getStorePath());
        byte[] bytes = StreamUtils.copyToByteArray(inputStream);
        // 设置返回response
        FileUtil.setHttpServletResponse(bytes, sysFileInfo.getFileName());
    }

    @Override
    public SysFileInfoVO updateImage(Long id, MultipartFile multipartFile, SysFileInfoFromDTO sysFileInfoFromDTO) {
        SysFileInfo sysFileInfo = this.getFileById(id);
        log.info("原图片地址：{}", sysFileInfo.getWebServerUrl() + sysFileInfo.getStorePath());
        // 删除原图片
        FastDFSUtil.deleteFile(sysFileInfo.getStorePath());
        if (sysFileInfoFromDTO.getThumbnail()) {
            // 压缩图片
            byte[] bytes = ImageUtil.compressImage(multipartFile, sysFileInfoFromDTO);
            // 上传压缩后的图片
            String storePath = MinioUtil.uploadFile(multipartFile.getOriginalFilename(), new ByteArrayInputStream(bytes));
            log.info("新图片地址：{}", MinioUtil.getWebServerUrl() + storePath);
            // 上传缩略图
            return this.saveNewFile(sysFileInfo, MinioUtil.getWebServerUrl(), storePath, multipartFile, true);
        }
        String storePath = MinioUtil.uploadFile(multipartFile);
        log.info("新图片地址：{}", MinioUtil.getWebServerUrl() + storePath);
        // 上传未压缩图片
        return this.saveNewFile(sysFileInfo, MinioUtil.getWebServerUrl(), storePath, multipartFile, false);
    }

    @Override
    public SysFileInfoVO updateFile(Long id, MultipartFile multipartFile) {
        SysFileInfo sysFileInfo = this.getFileById(id);
        log.info("原文件地址：{}", sysFileInfo.getWebServerUrl() + sysFileInfo.getStorePath());
        // 删除原文件
        MinioUtil.deleteFile(sysFileInfo.getStorePath());
        // 上传新文件
        String storePath = MinioUtil.uploadFile(multipartFile);
        log.info("新文件地址：{}", MinioUtil.getWebServerUrl() + storePath);
        // 上传未压缩文件
        return this.saveNewFile(sysFileInfo, MinioUtil.getWebServerUrl(), storePath, multipartFile, false);
    }

    @Override
    public void deleteFile(Long id) {
        SysFileInfo sysFileInfo = this.getFileById(id);
        log.info("删除文件地址：{}", sysFileInfo.getWebServerUrl() + sysFileInfo.getStorePath());
        // 删除原文件
        MinioUtil.deleteFile(sysFileInfo.getStorePath());
        // 删除文件成功后，再删除数据库记录
        fileRepository.delete(sysFileInfo);
    }

    private SysFileInfo getFileById(Long id) {
        // 查询文件信息
        Optional<SysFileInfo> fileOptional = fileRepository.findById(id);
        if (!fileOptional.isPresent()) {
            throw new RuntimeException(ResponseEnum.FILE_NOT_EXIST.name());
        }
        return fileOptional.get();
    }

    private SysFileInfoVO saveFile(String webServerUrl, String storePath, MultipartFile multipartFile, Boolean result) {
        SysFileInfo sysFileInfo = FileAssembly.toDomain(webServerUrl, storePath, multipartFile);
        sysFileInfo.setCompression(result);
        sysFileInfo = fileRepository.save(sysFileInfo);
        return SysFileInfoVO.builder()
                .id(sysFileInfo.getId())
                .fileUrl(sysFileInfo.getWebServerUrl() + sysFileInfo.getStorePath())
                .build();
    }

    private SysFileInfoVO saveNewFile(SysFileInfo sysFileInfo, String webServerUrl, String storePath, MultipartFile multipartFile, Boolean result) {
        SysFileInfo newSysFileInfo = FileAssembly.toDomain(sysFileInfo, webServerUrl, storePath, multipartFile);
        newSysFileInfo.setCompression(result);
        newSysFileInfo = fileRepository.save(newSysFileInfo);
        return SysFileInfoVO.builder()
                .id(newSysFileInfo.getId())
                .fileUrl(newSysFileInfo.getWebServerUrl() + newSysFileInfo.getStorePath())
                .build();
    }

}
