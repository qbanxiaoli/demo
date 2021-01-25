package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-15 19:59
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum {

    SUCCESS(true, 200, "SUCCESS"),//成功
    SUCCESS_VARIABLE(true, 200, "SUCCESS_VARIABLE"),//传入成功变量信息
    PARAMETER_ERROR(false, 400, "PARAMETER_ERROR"),//参数错误
    PARAMETER_NOT_NULL(false, 400, "PARAMETER_NOT_NULL"),//{0}不能为空
    TOKEN_ERROR(false, 500, "TOKEN_ERROR"),//token格式错误
    TOKEN_NOT_NULL(false, 400, "TOKEN_NOT_NULL"),//token不能为空
    TOKEN_EXPIRED(false, 402, "TOKEN_EXPIRED"),//token已失效
    FAILURE(false, 500, "FAILURE"),//失败
    FAILURE_VARIABLE(false, 500, "FAILURE_VARIABLE"),//传入失败变量信息
    SYSTEM_ERROR(false, 500, "SYSTEM_ERROR"),//系统异常
    MAX_FILE_SIZE(false, 500, "MAX_FILE_SIZE"),//单次上传文件大小不能超过10M
    FILE_CONTENT_TYPE_ERROR(false, 500, "FILE_CONTENT_TYPE_ERROR"),//上传文件类型错误
    FILE_NOT_EXIST(false, 500, "FILE_NOT_EXIST"),//文件不存在
    USER_NOT_LOGIN(false, 401, "USER_NOT_LOGIN"),//用户未登录
    ACCESS_DENY(false, 403, "ACCESS_DENY"),//访问权限不足
    USERNAME_NOT_NULL(false, 400, "USERNAME_NOT_NULL"),//用户名不能为空
    USERNAME_NOT_EXIST(false, 500, "USERNAME_NOT_EXIST"),//用户名不存在
    CLIENT_ERROR(false, 500, "CLIENT_ERROR"),//客户端信息错误
    PASSWORD_ERROR(false, 500, "PASSWORD_ERROR"),//密码错误
    USERNAME_EXIST(false, 500, "USERNAME_EXIST"),//用户名已存在
    ACCOUNT_NOT_EXIST(false, 500, "ACCOUNT_NOT_EXIST"),//账户不存在
    ACCOUNT_EXIST(false, 500, "ACCOUNT_EXIST"),//账户已存在
    DATA_EXIST(false, 500, "DATA_EXIST");//数据已存在

    private final Boolean result;

    private final Integer code;

    private final String message;

}