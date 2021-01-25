package com.exception;

import com.enums.ResponseEnum;
import com.google.common.collect.Maps;
import com.model.vo.ResponseVO;
import com.querydsl.core.QueryException;
import com.util.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Q版小李
 * @description 统一异常处理
 * @create 2018/8/8 11:35
 */
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseVO handleConstraintViolationException(ConstraintViolationException e) {
        log.error("参数校验异常:", e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        Map<String, String> map = Maps.newHashMap();
        while (iterator.hasNext()) {
            ConstraintViolation<?> constraintViolation = iterator.next();
            String name = constraintViolation.getPropertyPath().toString();
            log.error(name + ":" + constraintViolation);
            map.put(name.substring(name.indexOf(".") + 1), I18nUtil.getMessage(constraintViolation.getMessage()));
        }
        return new ResponseVO<>(ResponseEnum.PARAMETER_ERROR, map);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常：", e);
        return this.handleParameterException(e.getBindingResult().getFieldErrors());
    }

    @ExceptionHandler(BindException.class)
    public ResponseVO handleBindException(BindException e) {
        log.error("参数校验异常：", e);
        return this.handleParameterException(e.getBindingResult().getFieldErrors());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseVO handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("参数校验异常：", e);
        return new ResponseVO<>(ResponseEnum.PARAMETER_NOT_NULL, new Object[]{e.getParameterName()});
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseVO handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        log.error("参数校验异常：", e);
        return new ResponseVO<>(ResponseEnum.PARAMETER_NOT_NULL, new Object[]{e.getRequestPartName()});
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseVO handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("单次上传文件大小不能超过10M：", e);
        return new ResponseVO<>(ResponseEnum.MAX_FILE_SIZE);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseVO handleRuntimeException(RuntimeException e) {
        // 判断是否自定义枚举异常
        if (EnumUtils.isValidEnum(ResponseEnum.class, e.getMessage())) {
            log.error("捕获到自定义RuntimeException异常：{}", I18nUtil.getMessage(e.getMessage()));
            return new ResponseVO<>(EnumUtils.getEnum(ResponseEnum.class, e.getMessage()));
        }
        log.error("捕获到RuntimeException异常：", e);
        return new ResponseVO<>(ResponseEnum.SYSTEM_ERROR);
    }

    private ResponseVO handleParameterException(List<FieldError> fieldErrors) {
        Map<String, String> map = Maps.newHashMap();
        for (FieldError errors : fieldErrors) {
            log.error(errors.getField() + ":" + I18nUtil.getMessage(errors.getDefaultMessage()));
            map.put(errors.getField(), I18nUtil.getMessage(errors.getDefaultMessage()));
        }
        return new ResponseVO<>(ResponseEnum.PARAMETER_ERROR, map);
    }

}
