package com.util;

import com.alibaba.fastjson.JSON;
import com.enums.AuthoritiesEnum;
import com.model.vo.JwtVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qbanxiaolis
 * @description
 * @create 2018/12/14 10:44
 */
@Component
public class JwtUtil {

    private static HttpServletRequest request;

    public JwtUtil(HttpServletRequest request) {
        JwtUtil.request = request;
    }

    private static String getAccessToken() {
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = new OAuth2AuthenticationDetails(request);
        return oAuth2AuthenticationDetails.getTokenValue();
    }

    private static JwtVO parseJwt() {
        Jwt jwt = JwtHelper.decode(JwtUtil.getAccessToken());
        return JSON.parseObject(jwt.getClaims(), JwtVO.class);
    }

    public static String getUsername() {
        if (StringUtils.isNotBlank(JwtUtil.getAccessToken())) {
            return parseJwt().getUsername();
        } else {
            return AuthoritiesEnum.ANONYMOUS.getDescription();
        }
    }

    public static Long getUserId() {
        return JwtUtil.parseJwt().getId();
    }

}
