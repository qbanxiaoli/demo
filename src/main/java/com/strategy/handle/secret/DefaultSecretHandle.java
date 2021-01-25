package com.strategy.handle.secret;

import com.strategy.SecretHandle;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * @author Q版小李
 * @description
 * @create 2020/7/21 18:23
 */
@Service
@AllArgsConstructor
public class DefaultSecretHandle implements SecretHandle {

    private final StringEncryptor stringEncryptor;

    @Override
    @SneakyThrows
    public void encrypt(Object object, Field field) {
        // 加密
        Object fieldValue = FieldUtils.getFieldValue(object, field.getName());
        if (fieldValue != null && StringUtils.isNotBlank(fieldValue.toString())) {
            // 开启私有属性访问权限
            field.setAccessible(true);
            field.set(object, stringEncryptor.encrypt(fieldValue.toString()));
        }
    }

    @Override
    @SneakyThrows
    public void decrypt(Object object, Field field) {
        // 解密
        Object fieldValue = FieldUtils.getFieldValue(object, field.getName());
        if (fieldValue != null && StringUtils.isNotBlank(fieldValue.toString())) {
            // 开启私有属性访问权限
            field.setAccessible(true);
            field.set(object, stringEncryptor.decrypt(fieldValue.toString()));
        }
    }

}
