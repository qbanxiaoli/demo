package com.dao.mapper;

import com.dao.TkMapper;
import com.model.entity.SysFileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-17 23:38
 */
@Mapper
@Repository
public interface SysFileInfoMapper extends TkMapper<SysFileInfo> {

}
