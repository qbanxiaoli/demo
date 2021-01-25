package com.model.converter;

import com.model.dto.SysDictFromDTO;
import com.model.entity.SysDict;
import org.springframework.beans.BeanUtils;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-16 23:29
 */
public class SysDictAssembly {

    public static SysDict toDomain(SysDictFromDTO sysDictFromDTO) {
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(sysDictFromDTO, sysDict);
        return sysDict;
    }

}
