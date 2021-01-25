package com.controller;

import com.enums.ResponseEnum;
import com.model.vo.ResponseVO;
import com.model.vo.wechat.TokenVO;
import com.model.vo.wechat.UserInfoVO;
import com.model.vo.wechat.UserListVO;
import com.service.WeChatOfficialAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-28 10:39
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "微信公众号模块")
@RequestMapping("/we-chat/office-account")
public class WeChatOfficialAccountController {

    private final WeChatOfficialAccountService weChatOfficialAccountService;

    @ApiOperation("获取access_token")
    @GetMapping("/token")
    public ResponseVO<TokenVO> getAccessToken() {
        TokenVO tokenVO = weChatOfficialAccountService.getAccessToken();
        return new ResponseVO<>(ResponseEnum.SUCCESS, tokenVO);
    }

    @ApiOperation("获取用户基本信息")
    @GetMapping("/user-info")
    public ResponseVO<UserInfoVO> getUserInfo(@ApiParam(name = "accessToken", value = "调用接口凭证", required = true)
                                              @RequestParam(name = "accessToken") String accessToken,
                                              @ApiParam(name = "openid", value = "用户openid", required = true)
                                              @RequestParam(name = "openid") String openid) {
        UserInfoVO userInfoVO = weChatOfficialAccountService.getUserInfo(accessToken, openid);
        return new ResponseVO<>(ResponseEnum.SUCCESS, userInfoVO);
    }

    @ApiOperation("获取关注者列表")
    @GetMapping("/user/list")
    public ResponseVO<UserListVO> getUserList(@ApiParam(name = "accessToken", value = "调用接口凭证", required = true)
                                              @RequestParam(name = "accessToken") String accessToken,
                                              @ApiParam(name = "nextOpenid", value = "第一个拉取的OPENID，不填默认从头开始拉取")
                                              @RequestParam(name = "nextOpenid", required = false) String nextOpenid) {
        UserListVO userListVO = weChatOfficialAccountService.getUserList(accessToken, nextOpenid);
        return new ResponseVO<>(ResponseEnum.SUCCESS, userListVO);
    }

}
