package com.strategy;

import java.lang.reflect.Field;

/**
 * @author Q版小李
 * @description
 * @create 2020/7/21 18:23
 */
public interface SecretHandle {

    // 加密
    void encrypt(Object object, Field field);

    // 解密
    void decrypt(Object object, Field field);

}
