package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-02 00:10
 */
@Getter
@AllArgsConstructor
public enum BusinessTypeEnum {

    REGISTER(0, "注册"),
    LOGIN(1, "登录"),
    UPDATE_PASSWORD(2, "修改密码");

    private final Integer value;

    private final String description;

}
