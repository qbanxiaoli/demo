package com.aop.annotation;

import com.strategy.enums.SecretTypeEnum;

import java.lang.annotation.*;

/**
 * @author Q版小李
 * @description 属性加解密
 * @create 2020/7/20 18:35
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Security {

    /**
     * 自定义加密算法类型
     */
    SecretTypeEnum type() default SecretTypeEnum.DEFAULT;

}
