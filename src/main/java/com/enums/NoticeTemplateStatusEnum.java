package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-01 22:56
 */
@Getter
@AllArgsConstructor
public enum NoticeTemplateStatusEnum {

    DRAFT(0, "草稿"),
    UN_CHECKED(1, "待审核"),
    CHECKED_NOT_PASS(2, "审核未通过"),
    ENABLED(3, "启用"),
    DISABLED(4, "禁用");

    private final Integer value;

    private final String description;

}
