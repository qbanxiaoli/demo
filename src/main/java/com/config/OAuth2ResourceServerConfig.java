package com.config;

import com.exception.CustomAccessDeniedHandler;
import com.exception.CustomAuthenticationEntryPoint;
import com.enums.AuthoritiesEnum;
import com.jwt.JwtAccessToken;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsUtils;

/**
 * @author qbanxiaoli
 * @description 资源服务器
 * @create 2018/12/13 20:59
 */
@Configuration
@AllArgsConstructor
@EnableResourceServer
// 启用spring security的权限注解
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final JwtAccessToken jwtAccessToken;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(new JwtTokenStore(jwtAccessToken));
        resources.tokenServices(defaultTokenServices)
                // 添加未授权处理
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                // 权限不足处理
                .accessDeniedHandler(customAccessDeniedHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http    // 由于使用的是JWT，这里不需要csrf
                .csrf().disable()
                // 基于token,所以不需要session,以提升操作性能
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                // 对于preflight预检请求都放行
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .and().authorizeRequests()
                // 排除swagger文档拦截
                .antMatchers("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html", "/doc.html").permitAll()
                .antMatchers("/user/**", "/market/**").permitAll()
                .antMatchers("/contract/**").permitAll()
                .antMatchers("/file/**").hasAnyAuthority(AuthoritiesEnum.ADMIN.getRole(), AuthoritiesEnum.USER.getRole())
                .antMatchers("/account/**").hasAnyRole(AuthoritiesEnum.ADMIN.name(), AuthoritiesEnum.USER.name())
                .antMatchers("/notice/**").access("#oauth2.hasScope('read') and hasRole('ADMIN')")
                .antMatchers("/websocket/**").permitAll()
                .antMatchers("/we-chat/**").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
    }

}
