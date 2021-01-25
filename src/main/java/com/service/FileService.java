package com.service;

import com.model.dto.SysFileInfoFromDTO;
import com.model.dto.QueryDTO;
import com.model.entity.SysFileInfo;
import com.model.vo.SysFileInfoVO;
import com.model.vo.PageVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/10/19 9:54 PM
 */
public interface FileService {

    // 查询文件访问路径
    SysFileInfoVO getFileUrl(Long id);

    // 查询文件列表
    List<SysFileInfo> getFileList();

    // 分页查询文件列表
    PageVO<SysFileInfo> pagingFile(QueryDTO queryDTO);

    // 上传图片
    SysFileInfoVO uploadImage(MultipartFile multipartFile, SysFileInfoFromDTO sysFileInfoFromDTO);

    // 上传文件
    SysFileInfoVO uploadFile(MultipartFile multipartFile);

    // 下载文件
    void downloadFile(Long id);

    // 更新图片
    SysFileInfoVO updateImage(Long id, MultipartFile multipartFile, SysFileInfoFromDTO sysFileInfoFromDTO);

    // 更新文件
    SysFileInfoVO updateFile(Long id, MultipartFile multipartFile);

    // 删除文件
    void deleteFile(Long id);

}
