package com.dao.querydsl;

import com.model.dto.SysDictDTO;
import com.model.vo.SysDictVO;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-01-15 13:07
 */
public interface SysDictDao {

    List<SysDictVO> getSysDictList(SysDictDTO sysDictDTO);

    List<SysDictVO> getAllSysDictList(SysDictDTO sysDictDTO);

}
