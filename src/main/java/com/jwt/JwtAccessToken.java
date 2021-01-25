package com.jwt;

import com.model.entity.QSysUser;
import com.model.entity.SysUser;
import com.dao.repository.SysUserRepository;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/11/22 20:56
 */
@Component
@AllArgsConstructor
public class JwtAccessToken extends JwtAccessTokenConverter {

    private final SysUserRepository sysUserRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        // 获取当前用户信息
        Object principal = authentication.getPrincipal();
        SysUser sysUser;
        if (principal instanceof SysUser) {
            sysUser = (SysUser) principal;
        } else { // 刷新token时使用
            sysUser = sysUserRepository.findByUsername(principal.toString());
        }
        if (sysUser != null) {
            List<String> authorities = Lists.newArrayList();
            sysUser.getAuthorities().forEach(authority -> authorities.add(authority.getAuthority()));
            // 将用户信息添加到token额外信息中
            defaultOAuth2AccessToken.getAdditionalInformation().put(QSysUser.sysUser.id.getMetadata().getName(), sysUser.getId());
            defaultOAuth2AccessToken.getAdditionalInformation().put(QSysUser.sysUser.authorities.getMetadata().getName(), authorities);
        }
        return super.enhance(defaultOAuth2AccessToken, authentication);
    }

}