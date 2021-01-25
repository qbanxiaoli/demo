package com.config;

import com.exception.CustomAccessDeniedHandler;
import com.exception.CustomAuthenticationEntryPoint;
import com.exception.CustomWebResponseExceptionTranslator;
import com.jwt.JwtAccessToken;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author qbanxiaoli
 * @description 授权服务器
 * @create 2018/8/12 下午6:18
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;

    private final JwtAccessToken jwtAccessToken;

    private final AuthenticationManager authenticationManager;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final AuthorizationServerProperties authorizationServerProperties;

    private final CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 使用JdbcClientDetailsService客户端详情服务
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    //配置认证管理器以及用户信息业务实现
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // 导入证书
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(authorizationServerProperties.getJwt().getKeyStore()), authorizationServerProperties.getJwt().getKeyPassword().toCharArray());
        // 使用非对称加密算法来对Token进行签名,默认使用对称加密
        jwtAccessToken.setKeyPair(keyStoreKeyFactory.getKeyPair(authorizationServerProperties.getJwt().getKeyAlias()));
        endpoints
                // 使用自定义tokenServices
                .tokenServices(tokenServices())
                // 配置认证管理器
                .authenticationManager(authenticationManager)
                // 配置JwtAccessToken转换器，使用jwt非对称加密
                .accessTokenConverter(jwtAccessToken)
                .exceptionTranslator(customWebResponseExceptionTranslator);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .passwordEncoder(new BCryptPasswordEncoder())
                // 开启/oauth/token_key获取公钥无权限访问
                .tokenKeyAccess("permitAll()")
                // 开启/oauth/check_token检查token无权限访问
                .checkTokenAccess("permitAll()")
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .allowFormAuthenticationForClients();
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(jwtAccessToken));
        return tokenEnhancerChain;
    }

    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(new JwtTokenStore(jwtAccessToken));
        // 开启refresh_token刷新功能支持
        defaultTokenServices.setSupportRefreshToken(true);
        // 设置refresh_token过期时间
        defaultTokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(24));
        // 是否复用refresh_token，每次刷新token后refresh_token的jti(token id)保持不变
        defaultTokenServices.setReuseRefreshToken(true);
        // 设置access_token过期时间
        defaultTokenServices.setAccessTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(12));
        // 不设置这个，jwt就会失效
        defaultTokenServices.setTokenEnhancer(tokenEnhancer());
        return defaultTokenServices;
    }

}
