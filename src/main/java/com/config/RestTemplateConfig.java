package com.config;

import com.properties.ProxyProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/12/21 3:04 PM
 */
@AllArgsConstructor
@Configuration
public class RestTemplateConfig {

    private final ProxyProperties proxyProperties;

    @Bean
    @Primary
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // 这里处理编码问题 请求微信接口时不指定编码 返回的昵称、地区等信息可能会乱码
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> messageConverter : messageConverters) {
            if (messageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) messageConverter).setDefaultCharset(StandardCharsets.UTF_8);
            }
            if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
                ((MappingJackson2HttpMessageConverter) messageConverter).setDefaultCharset(StandardCharsets.UTF_8);
            }
            if (messageConverter instanceof AllEncompassingFormHttpMessageConverter) {
                ((AllEncompassingFormHttpMessageConverter) messageConverter).setCharset(StandardCharsets.UTF_8);
            }
        }
        return restTemplate;
    }

    @Bean
    RestTemplate sslRestTemplate() {
        //设置代理
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyProperties.getHost(), proxyProperties.getPort())));
        return new RestTemplate(simpleClientHttpRequestFactory);
    }

}
