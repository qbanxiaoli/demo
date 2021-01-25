package com.model.converter;

import com.google.common.collect.Lists;
import com.model.entity.SysUser;
import com.model.vo.UserVO;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-25 18:35
 */
public class UserAssembly {

    public static UserVO toDomain(SysUser sysUser) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(sysUser, userVO);
        return userVO;
    }

    public static List<UserVO> toDomain(List<SysUser> sysUserList) {
        List<UserVO> userVOList = Lists.newArrayList();
        sysUserList.forEach(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOList.add(userVO);
        });
        return userVOList;
    }

}
