package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-01 22:52
 */
@Getter
@AllArgsConstructor
public enum NoticeTypeEnum {

    SMS(1, "短信"),
    EMAIL(1, "邮件");

    private final Integer value;

    private final String description;

}
