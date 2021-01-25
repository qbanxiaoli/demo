package com.controller;

import com.enums.ResponseEnum;
import com.model.dto.SysDictDTO;
import com.model.dto.SysDictQueryDTO;
import com.model.entity.SysDict;
import com.model.vo.PageVO;
import com.model.vo.ResponseVO;
import com.model.vo.SysDictVO;
import com.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-15 11:38
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "数据字典模块")
@RequestMapping("/sys_dict")
public class SysDictQueryController {

    private final SysDictService sysDictService;

    @ApiOperation("查询个人数据字典列表")
    @PostMapping("/list")
    public ResponseVO<List<SysDictVO>> getSysDictList(@ApiParam(name = "sysDictDTO", value = "动态数据字典查询请求模型", required = true)
                                                      @Validated @RequestBody SysDictDTO sysDictDTO) {
        List<SysDictVO> sysDictVOList = sysDictService.getSysDictList(sysDictDTO);
        return new ResponseVO<>(ResponseEnum.SUCCESS, sysDictVOList);
    }

    @ApiOperation("查询全部数据字典列表")
    @PostMapping("/all/list")
    public ResponseVO<List<SysDictVO>> getAllSysDictList(@ApiParam(name = "sysDictDTO", value = "动态数据字典查询请求模型", required = true)
                                                         @Validated @RequestBody SysDictDTO sysDictDTO) {
        List<SysDictVO> sysDictVOList = sysDictService.getAllSysDictList(sysDictDTO);
        return new ResponseVO<>(ResponseEnum.SUCCESS, sysDictVOList);
    }

    @ApiOperation("分页查询数据字典列表")
    @PostMapping("/paging")
    public ResponseVO<PageVO<SysDict>> pagingSysDict(@ApiParam(name = "sysDictQueryDTO", value = "数据字典查询请求模型", required = true)
                                                     @Validated @RequestBody SysDictQueryDTO sysDictQueryDTO) {
        PageVO<SysDict> sysDictList = sysDictService.pagingSysDict(sysDictQueryDTO);
        return new ResponseVO<>(ResponseEnum.SUCCESS, sysDictList);
    }

}
