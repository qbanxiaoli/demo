package com.strategy.util;

import com.enums.OperateTypeEnum;
import com.strategy.OperateHandle;
import org.springframework.stereotype.Component;

import java.beans.Introspector;
import java.util.Map;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-13 05:33
 */
@Component
public class OperateUtil {

    private static Map<String, OperateHandle> map;

    public OperateUtil(Map<String, OperateHandle> map) {
        OperateUtil.map = map;
    }

    /**
     * @param operateType 操作类型枚举
     * @return 返回对应的操作处理器
     * @author qbanxiaoli
     * @description 根据bean名字从map中获取对应的bean
     */
    public static OperateHandle getOperateHandle(OperateTypeEnum operateType) {
        // 将类名的首字母转换成小写
        String beanName = Introspector.decapitalize(operateType.getValue());
        return map.get(beanName);
    }

}
