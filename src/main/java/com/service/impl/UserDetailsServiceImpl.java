package com.service.impl;

import com.dao.repository.SysUserRepository;
import com.enums.ResponseEnum;
import com.model.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/12/7 16:03
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (StringUtils.equals(username, "NONE_PROVIDED")) {
            throw new RuntimeException(ResponseEnum.USERNAME_NOT_NULL.name());
        }
        SysUser sysUser = sysUserRepository.findByUsername(username);
        if (sysUser == null) {
            throw new RuntimeException(ResponseEnum.USERNAME_NOT_EXIST.name());
        }
        return sysUser;
    }

}
