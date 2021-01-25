package com.service;

import com.model.dto.SysDictDTO;
import com.model.dto.SysDictFromDTO;
import com.model.dto.SysDictQueryDTO;
import com.model.entity.SysDict;
import com.model.vo.PageVO;
import com.model.vo.SysDictVO;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-15 11:44
 */
public interface SysDictService {

    // 查询个人数据字典列表
    List<SysDictVO> getSysDictList(SysDictDTO sysDictDTO);

    // 查询全部数据字典列表
    List<SysDictVO> getAllSysDictList(SysDictDTO sysDictDTO);

    // 分页查询数据字典
    PageVO<SysDict> pagingSysDict(SysDictQueryDTO sysDictQueryDTO);

    // 添加数据字典
    void addSysDict(SysDictFromDTO sysDictFromDTO);

}
