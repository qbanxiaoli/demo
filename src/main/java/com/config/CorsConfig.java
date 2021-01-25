package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author qbanxiaoli
 * @description 跨域配置
 * @create 2019-11-15 20:47
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                //设置允许的方法
                .allowedMethods("*")
                //设置允许的请求头
                .allowedHeaders("*")
                //设置响应报头
                .exposedHeaders(HttpHeaders.CONTENT_DISPOSITION)
                //是否允许证书，不再默认开启
                .allowCredentials(true)
                //跨域允许时间
                .maxAge(1800);
    }

}