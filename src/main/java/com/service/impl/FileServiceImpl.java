package com.service.impl;

import com.dao.mapper.SysFileInfoMapper;
import com.dao.repository.FileRepository;
import com.enums.ResponseEnum;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.model.converter.FileAssembly;
import com.model.dto.SysFileInfoFromDTO;
import com.model.dto.QueryDTO;
import com.model.entity.QSysFileInfo;
import com.model.entity.SysFileInfo;
import com.model.vo.SysFileInfoVO;
import com.model.vo.PageVO;
import com.service.FileService;
import com.util.FastDFSUtil;
import com.util.FileUtil;
import com.util.ImageUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Optional;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/10/19 9:57 PM
 */
@Slf4j
@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final SysFileInfoMapper sysFileInfoMapper;

    private final FileRepository fileRepository;

    @Override
    public SysFileInfoVO getFileUrl(Long id) {
        SysFileInfo sysFileInfo = getFileById(id);
        return SysFileInfoVO.builder()
                .id(id)
                .fileUrl(sysFileInfo.getWebServerUrl() + sysFileInfo.getStorePath())
                .build();
    }

    @Override
    public List<SysFileInfo> getFileList() {
        return fileRepository.findAll();
    }

    @Override
    public PageVO<SysFileInfo> pagingFile(QueryDTO queryDTO) {
        // 使用PageHelper分页插件进行分页查询，只对下一条的查询结果进行分页
        Page<?> page = PageHelper.startPage(queryDTO.getCurrentPage(), queryDTO.getPageSize());
        // 动态查询条件拼装
        Example example = new Example(SysFileInfo.class);
        Example.Criteria criteria = example.createCriteria();
        // 文件名关键字查询
        if (StringUtils.isNotBlank(queryDTO.getKeyword())) {
            criteria.andLike(QSysFileInfo.sysFileInfo.fileName.getMetadata().getName(), queryDTO.getKeyword() + "%");
        }
        // 开始时间
        if (queryDTO.getStartTime() != null) {
            criteria.andGreaterThanOrEqualTo(QSysFileInfo.sysFileInfo.gmtCreated.getMetadata().getName(), queryDTO.getStartTime());
        }
        // 结束时间
        if (queryDTO.getEndTime() != null) {
            criteria.andLessThanOrEqualTo(QSysFileInfo.sysFileInfo.gmtCreated.getMetadata().getName(), queryDTO.getEndTime());
        }
        // 排序规则
        if (queryDTO.getOrder()) {
            example.orderBy(QSysFileInfo.sysFileInfo.gmtCreated.getMetadata().getName()).desc();
        }
        // 分页查询文件列表
        List<SysFileInfo> sysFileInfoList = sysFileInfoMapper.selectByExample(example);
        return PageVO.<SysFileInfo>builder()
                .currentPage(queryDTO.getCurrentPage())
                .pageSize(queryDTO.getPageSize())
                .total(page.getTotal())
                .results(sysFileInfoList)
                .build();
    }

    @Override
    public SysFileInfoVO uploadImage(MultipartFile multipartFile, SysFileInfoFromDTO sysFileInfoFromDTO) {
        if (sysFileInfoFromDTO.getThumbnail()) {
            // 压缩图片
            byte[] bytes = ImageUtil.compressImage(multipartFile, sysFileInfoFromDTO);
            // 上传压缩后的图片
            String storePath = FastDFSUtil.uploadFile(bytes, FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
            log.info("上传缩略图地址：{}", FastDFSUtil.getWebServerUrl() + storePath);
            // 上传缩略图
            return this.saveFile(storePath, multipartFile, true);
        }
        log.info("上传图片大小：{}B", multipartFile.getSize());
        String storePath = FastDFSUtil.uploadFile(multipartFile);
        log.info("上传图片地址：{}", FastDFSUtil.getWebServerUrl() + storePath);
        // 上传原图
        return this.saveFile(storePath, multipartFile, false);
    }

    @Override
    public SysFileInfoVO uploadFile(MultipartFile multipartFile) {
        // 上传文件
        String storePath = FastDFSUtil.uploadFile(multipartFile);
        log.info("上传文件地址：{}", FastDFSUtil.getWebServerUrl() + storePath);
        // 上传原文件
        return this.saveFile(storePath, multipartFile, false);
    }

    @Override
    @SneakyThrows
    public void downloadFile(Long id) {
        SysFileInfo sysFileInfo = this.getFileById(id);
        log.info("文件访问地址：{}", sysFileInfo.getWebServerUrl() + sysFileInfo.getStorePath());
        byte[] bytes = FastDFSUtil.downloadFile(sysFileInfo.getStorePath());
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
            String storePath = FastDFSUtil.uploadFile(bytes, FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
            log.info("新图片地址：{}", FastDFSUtil.getWebServerUrl() + storePath);
            // 上传缩略图
            return this.saveNewFile(sysFileInfo, storePath, multipartFile, true);
        }
        String storePath = FastDFSUtil.uploadFile(multipartFile);
        log.info("新图片地址：{}", FastDFSUtil.getWebServerUrl() + storePath);
        // 上传未压缩图片
        return this.saveNewFile(sysFileInfo, storePath, multipartFile, false);
    }

    @Override
    public SysFileInfoVO updateFile(Long id, MultipartFile multipartFile) {
        SysFileInfo sysFileInfo = this.getFileById(id);
        log.info("原文件地址：{}", sysFileInfo.getWebServerUrl() + sysFileInfo.getStorePath());
        // 删除原文件
        FastDFSUtil.deleteFile(sysFileInfo.getStorePath());
        // 上传新文件
        String storePath = FastDFSUtil.uploadFile(multipartFile);
        log.info("新文件地址：{}", FastDFSUtil.getWebServerUrl() + storePath);
        // 上传未压缩文件
        return this.saveNewFile(sysFileInfo, storePath, multipartFile, false);
    }

    @Override
    public void deleteFile(Long id) {
        SysFileInfo sysFileInfo = this.getFileById(id);
        log.info("删除文件地址：{}", sysFileInfo.getWebServerUrl() + sysFileInfo.getStorePath());
        // 删除原文件
        FastDFSUtil.deleteFile(sysFileInfo.getStorePath());
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

    private SysFileInfoVO saveFile(String storePath, MultipartFile multipartFile, Boolean result) {
        SysFileInfo sysFileInfo = FileAssembly.toDomain(storePath, multipartFile);
        sysFileInfo.setCompression(result);
        sysFileInfo = fileRepository.save(sysFileInfo);
        return SysFileInfoVO.builder()
                .id(sysFileInfo.getId())
                .fileUrl(sysFileInfo.getWebServerUrl() + sysFileInfo.getStorePath())
                .build();
    }

    private SysFileInfoVO saveNewFile(SysFileInfo sysFileInfo, String storePath, MultipartFile multipartFile, Boolean result) {
        SysFileInfo newSysFileInfo = FileAssembly.toDomain(sysFileInfo, storePath, multipartFile);
        newSysFileInfo.setCompression(result);
        newSysFileInfo = fileRepository.save(newSysFileInfo);
        return SysFileInfoVO.builder()
                .id(newSysFileInfo.getId())
                .fileUrl(newSysFileInfo.getWebServerUrl() + newSysFileInfo.getStorePath())
                .build();
    }

}
