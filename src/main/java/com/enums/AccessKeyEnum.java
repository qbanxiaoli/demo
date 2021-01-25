package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-11 01:59
 */
@Getter
@AllArgsConstructor
public enum AccessKeyEnum {

    ACCESS_KEY_ID("AccessKeyId", "用户id"),
    ACCESS_KEY_SECRET("AccessKeySecret", "用户secret");

    private final String value;

    private final String description;

}
