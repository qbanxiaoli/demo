package com.dao.repository;

import com.model.entity.SysUser;
import com.dao.BaseRepository;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-12-04 00:13
 */

public interface SysUserRepository extends BaseRepository<SysUser, Long> {

    SysUser findByUsername(String username);

}
