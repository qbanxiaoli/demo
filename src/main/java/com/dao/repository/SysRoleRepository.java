package com.dao.repository;

import com.model.entity.SysRole;
import com.dao.BaseRepository;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-12-05 15:18
 */
public interface SysRoleRepository extends BaseRepository<SysRole, Long> {

    SysRole findByAuthority(String role);

}
