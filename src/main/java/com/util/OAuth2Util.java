package com.util;

import com.model.entity.QSysUser;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/12/21 4:39 PM
 */
@Component
public class OAuth2Util {

    private static OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    private static AuthorizationServerProperties authorizationServerProperties;

    public OAuth2Util(OAuth2ResourceServerProperties oAuth2ResourceServerProperties, AuthorizationServerProperties authorizationServerProperties) {
        OAuth2Util.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
        OAuth2Util.authorizationServerProperties = authorizationServerProperties;
    }

    // 密码登录
    public static OAuth2AccessToken loginByPassword(String username, String password) {
        //授权请求信息
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put(QSysUser.sysUser.username.getMetadata().getName(), Collections.singletonList(username));
        map.put(QSysUser.sysUser.password.getMetadata().getName(), Collections.singletonList(password));
        map.put(OAuth2Utils.GRANT_TYPE, Collections.singletonList(QSysUser.sysUser.password.getMetadata().getName()));
        //获取token
        return OAuth2Util.getAccessToken(map);
    }

    // 刷新token
    public static OAuth2AccessToken refreshToken(String refreshToken) {
        //授权请求信息
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put(OAuth2AccessToken.REFRESH_TOKEN, Collections.singletonList(refreshToken));
        map.put(OAuth2Utils.GRANT_TYPE, Collections.singletonList(OAuth2AccessToken.REFRESH_TOKEN));
        //获取token
        return OAuth2Util.getAccessToken(map);
    }

    private static OAuth2AccessToken getAccessToken(MultiValueMap<String, String> map) {
        // 获取access_token
        Mono<OAuth2AccessToken> mono = WebClient.create()
                .post()
                .uri(authorizationServerProperties.getTokenKeyAccess())
                .headers(headers -> headers.setBasicAuth(oAuth2ResourceServerProperties.getOpaquetoken().getClientId(), oAuth2ResourceServerProperties.getOpaquetoken().getClientSecret()))
                .body(BodyInserters.fromFormData(map))
                .retrieve()
                .bodyToMono(OAuth2AccessToken.class);
        return mono.block();
    }

}
