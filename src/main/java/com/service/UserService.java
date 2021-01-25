package com.service;

import com.model.dto.QueryDTO;
import com.model.dto.RegisterFormDTO;
import com.model.vo.PageVO;
import com.model.vo.UserVO;

import java.util.List;


/**
 * @author terminus
 * @description
 * @create 2019/12/11 11:37 上午
 */
public interface UserService {

    // 用户注册
    void register(RegisterFormDTO registerFormDTO);

    // 查询用户信息
    UserVO getUserInfo();

    // 查询用户信息列表
    List<UserVO> getUserInfoList();

    // 分页查询用户信息列表
    PageVO<UserVO> pagingUserInfo(QueryDTO queryDTO);

}
