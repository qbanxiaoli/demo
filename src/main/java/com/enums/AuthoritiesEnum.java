package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-12-15 17:59
 */
@Getter
@AllArgsConstructor
public enum AuthoritiesEnum {

    ADMIN("ROLE_ADMIN", "管理员"),
    USER("ROLE_USER", "普通用户"),
    ANONYMOUS("ROLE_ANONYMOUS", "匿名用户");

    private final String role;

    private final String description;

}
