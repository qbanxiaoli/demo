package com.strategy.enums;

import com.strategy.handle.secret.DefaultSecretHandle;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-13 05:32
 */
@Getter
@AllArgsConstructor
public enum SecretTypeEnum {

    DEFAULT(DefaultSecretHandle.class.getSimpleName(), "默认加密方式");

    private final String value;

    private final String description;

}
