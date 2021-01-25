package com.strategy.util;

import com.strategy.SecretHandle;
import com.strategy.enums.SecretTypeEnum;
import org.springframework.stereotype.Component;

import java.beans.Introspector;
import java.util.Map;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-13 05:33
 */
@Component
public class SecretUtil {

    private static Map<String, SecretHandle> map;

    public SecretUtil(Map<String, SecretHandle> map) {
        SecretUtil.map = map;
    }

    /**
     * @param secretType 加密类型枚举
     * @return 返回对应的加密处理器
     * @author qbanxiaoli
     * @description 根据bean名字从map中获取对应的bean
     */
    public static SecretHandle getSecretHandle(SecretTypeEnum secretType) {
        // 将类名的首字母转换成小写
        String beanName = Introspector.decapitalize(secretType.getValue());
        return map.get(beanName);
    }

}
