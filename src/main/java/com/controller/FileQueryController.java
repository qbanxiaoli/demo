package com.controller;

import com.enums.ResponseEnum;
import com.model.dto.QueryDTO;
import com.model.entity.SysFileInfo;
import com.model.vo.SysFileInfoVO;
import com.model.vo.PageVO;
import com.model.vo.ResponseVO;
import com.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/10/19 9:06 PM
 */
@RestController
@AllArgsConstructor
@Api(tags = "文件模块")
@RequestMapping("/file")
public class FileQueryController {

    private final FileService fileService;

    @ApiOperation("查询文件访问路径")
    @GetMapping("/url/{id}")
    public ResponseVO<SysFileInfoVO> getFileUrl(@ApiParam(name = "id", value = "文件id", example = "1", required = true)
                                                @PathVariable(name = "id") Long id) {
        SysFileInfoVO sysFileInfoVO = fileService.getFileUrl(id);
        return new ResponseVO<>(ResponseEnum.SUCCESS, sysFileInfoVO);
    }

    @ApiOperation("分页查询文件列表")
    @PostMapping("/paging")
    public ResponseVO<PageVO<SysFileInfo>> pagingFile(@ApiParam(name = "queryDTO", value = "分页查询请求模型", required = true)
                                                      @Validated @RequestBody QueryDTO queryDTO) {
        PageVO<SysFileInfo> filePageVO = fileService.pagingFile(queryDTO);
        return new ResponseVO<>(ResponseEnum.SUCCESS, filePageVO);
    }

}
