package com.dao.repository;

import com.model.entity.SysDict;
import com.dao.BaseRepository;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-15 10:50
 */
public interface SysDictRepository extends BaseRepository<SysDict, Long> {

    SysDict findByTableNameAndFieldNameAndFieldValue(String tableName, String fieldName, Object fieldValue);

}
