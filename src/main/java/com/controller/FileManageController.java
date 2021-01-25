package com.controller;

import com.enums.ResponseEnum;
import com.model.dto.SysFileInfoFromDTO;
import com.model.entity.SysFileInfo;
import com.model.vo.SysFileInfoVO;
import com.model.vo.ResponseVO;
import com.service.FileService;
import com.util.FileUtil;
import com.util.ImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/10/19 9:06 PM
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "文件模块")
@RequestMapping("/file")
public class FileManageController {

    private final FileService fileService;

    @ApiOperation("上传图片")
    @PostMapping("/image/upload")
    public ResponseVO<SysFileInfoVO> uploadImage(@ApiParam(name = "file", value = "待上传图片", required = true)
                                                 @RequestPart(name = "file") MultipartFile multipartFile,
                                                 @ApiParam(name = "fileFromDTO", value = "图片上传数据请求模型")
                                                 @Validated @ModelAttribute SysFileInfoFromDTO sysFileInfoFromDTO) {
        if (!ImageUtil.isImage(multipartFile)) {
            log.warn("上传文件类型错误");
            return new ResponseVO<>(ResponseEnum.FILE_CONTENT_TYPE_ERROR);
        }
        // 上传图片
        SysFileInfoVO sysFileInfoVO = fileService.uploadImage(multipartFile, sysFileInfoFromDTO);
        return new ResponseVO<>(ResponseEnum.SUCCESS, sysFileInfoVO);
    }

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public ResponseVO<SysFileInfoVO> uploadFile(@ApiParam(name = "file", value = "待上传文件", required = true)
                                                @RequestPart(name = "file") MultipartFile multipartFile) {
        SysFileInfoVO sysFileInfoVO = fileService.uploadFile(multipartFile);
        return new ResponseVO<>(ResponseEnum.SUCCESS, sysFileInfoVO);
    }

    @ApiOperation("下载文件")
    @GetMapping("/download/{id}")
    public void downloadFile(@ApiParam(name = "id", value = "文件id", example = "1", required = true)
                             @PathVariable(name = "id") Long id) {
        fileService.downloadFile(id);
    }

    @ApiOperation("更新图片")
    @PutMapping("/image/update")
    public ResponseVO<SysFileInfoVO> updateImage(@ApiParam(name = "id", value = "图片id", example = "1", required = true)
                                                 @RequestParam(name = "id") Long id,
                                                 @ApiParam(name = "file", value = "待上传图片", required = true)
                                                 @RequestPart(name = "file") MultipartFile multipartFile,
                                                 @ApiParam(name = "fileFromDTO", value = "图片上传数据请求模型")
                                                 @Validated @ModelAttribute SysFileInfoFromDTO sysFileInfoFromDTO) {
        if (!ImageUtil.isImage(multipartFile)) {
            log.warn("上传文件类型错误");
            return new ResponseVO<>(ResponseEnum.FILE_CONTENT_TYPE_ERROR);
        }
        // 更新图片
        SysFileInfoVO sysFileInfoVO = fileService.updateImage(id, multipartFile, sysFileInfoFromDTO);
        return new ResponseVO<>(ResponseEnum.SUCCESS, sysFileInfoVO);
    }

    @ApiOperation("更新文件")
    @PutMapping("/update")
    public ResponseVO updateFile(@ApiParam(name = "id", value = "文件id", example = "1", required = true)
                                 @RequestParam(name = "id") Long id,
                                 @ApiParam(name = "file", value = "待上传文件", required = true)
                                 @RequestPart(name = "file") MultipartFile multipartFile) {
        SysFileInfoVO sysFileInfoVO = fileService.updateFile(id, multipartFile);
        return new ResponseVO<>(ResponseEnum.SUCCESS, sysFileInfoVO);
    }

    @ApiOperation("删除文件")
    @DeleteMapping("/delete/{id}")
    public ResponseVO deleteFile(@ApiParam(name = "id", value = "文件id", example = "1", required = true)
                                 @PathVariable(name = "id") Long id) {
        fileService.deleteFile(id);
        return new ResponseVO<>(ResponseEnum.SUCCESS);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("导出文件信息到excel文件")
    @GetMapping("/excel/export")
    public void exportFileInfoToExcel() {
        List<SysFileInfo> sysFileInfoList = fileService.getFileList();
        FileUtil.exportToExcel(sysFileInfoList, SysFileInfo.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("导出文件信息到csv文件")
    @GetMapping("/csv/export")
    public void exportFileInfoToCSV() {
        List<SysFileInfo> sysFileInfoList = fileService.getFileList();
        FileUtil.exportToCSV(sysFileInfoList, SysFileInfo.class);
    }

}
