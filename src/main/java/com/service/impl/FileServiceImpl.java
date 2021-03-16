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
import com.util.FastDFSUtil;
import com.util.FileUtil;
import com.util.ImageUtil;
import com.util.WaterMarkUtil;
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
public abstract class FileServiceImpl implements FileService {

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

    private SysFileInfo getFileById(Long id) {
        // 查询文件信息
        Optional<SysFileInfo> fileOptional = fileRepository.findById(id);
        if (!fileOptional.isPresent()) {
            throw new RuntimeException(ResponseEnum.FILE_NOT_EXIST.name());
        }
        return fileOptional.get();
    }

}
