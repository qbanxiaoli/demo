package com.service;

import com.model.vo.wechat.TokenVO;
import com.model.vo.wechat.UserInfoVO;
import com.model.vo.wechat.UserListVO;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-28 11:16
 */
public interface WeChatOfficialAccountService {

    TokenVO getAccessToken();

    UserInfoVO getUserInfo(String accessToken, String openid);

    UserListVO getUserList(String accessToken, String nextOpenid);

}
