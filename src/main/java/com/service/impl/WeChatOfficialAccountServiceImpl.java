package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.properties.OfficialAccountProperties;
import com.properties.WeChatProperties;
import com.model.vo.wechat.TokenVO;
import com.model.vo.wechat.UserInfoVO;
import com.model.vo.wechat.UserListVO;
import com.service.WeChatOfficialAccountService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-28 11:16
 */
@Slf4j
@Service
@AllArgsConstructor
public class WeChatOfficialAccountServiceImpl implements WeChatOfficialAccountService {

    private final RestTemplate restTemplate;

    private final WeChatProperties weChatProperties;

    private final OfficialAccountProperties officialAccountProperties;

    @Override
    public TokenVO getAccessToken() {
        URI uri = UriComponentsBuilder.fromHttpUrl(weChatProperties.getWeChatUrl() + officialAccountProperties.getAccessTokenUrl())
                .queryParam("grant_type", "client_credential")
                .queryParam("appid", weChatProperties.getAppId())
                .queryParam("secret", weChatProperties.getAppSecret())
                .build().encode().toUri();
        String result = restTemplate.getForObject(uri, String.class);
        return JSON.parseObject(result, TokenVO.class);
    }

    @Override
    @SneakyThrows
    public UserInfoVO getUserInfo(String accessToken, String openid) {
        URI uri = UriComponentsBuilder.fromHttpUrl(weChatProperties.getWeChatUrl() + officialAccountProperties.getUserInfo())
                .queryParam("access_token", accessToken)
                .queryParam("openid", openid)
                .build().encode().toUri();
        String result = restTemplate.getForObject(uri, String.class);
        return JSON.parseObject(result, UserInfoVO.class);
    }

    @Override
    public UserListVO getUserList(String accessToken, String nextOpenid) {
        URI uri = UriComponentsBuilder.fromHttpUrl(weChatProperties.getWeChatUrl() + officialAccountProperties.getUserList())
                .queryParam("access_token", accessToken)
                .queryParam("next_openid", nextOpenid)
                .build().encode().toUri();
        String result = restTemplate.getForObject(uri, String.class);
        return JSON.parseObject(result, UserListVO.class);
    }

}
