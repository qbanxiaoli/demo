package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;

/**
 * @author qbanxiaoli
 * @description 消息配置类
 * @create 2019-11-15 20:49
 */
@Configuration
public class ValidatorConfig implements WebMvcConfigurer {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        // 默认为普通失败，参数全部校验完成后才会返回，设置为快速失败后，参数校验失败后立即返回
        validator.getValidationPropertyMap().put("hibernate.validator.fail_fast", "false");
        validator.setValidationMessageSource(getMessageSource());
        return validator;
    }

    private ResourceBundleMessageSource getMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setBasenames("i18n/messages");
        messageSource.setCacheSeconds(10);
        return messageSource;
    }

}