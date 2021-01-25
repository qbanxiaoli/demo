package com.model.vo;

import com.enums.ResponseEnum;
import com.util.I18nUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-15 19:55
 */
@Data
@ApiModel(value = "数据返回模型")
public class ResponseVO<T> {

    @ApiModelProperty(value = "结果状态", required = true)
    private Boolean result;

    @ApiModelProperty(value = "结果码", required = true)
    private Integer code;

    @ApiModelProperty(value = "结果信息", required = true)
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;


    // 返回结果不包含数据，返回信息不包含变量
    public ResponseVO(ResponseEnum responseEnum) {
        this.result = responseEnum.getResult();
        this.code = responseEnum.getCode();
        this.message = I18nUtil.getMessage(responseEnum.getMessage());
    }

    // 返回结果包含数据，返回信息不包含变量
    public ResponseVO(ResponseEnum responseEnum, T data) {
        this.result = responseEnum.getResult();
        this.code = responseEnum.getCode();
        this.message = I18nUtil.getMessage(responseEnum.getMessage());
        this.data = data;
    }

    // 返回结果不包含数据，返回信息包含变量
    public ResponseVO(ResponseEnum responseEnum, Object[] args) {
        this.result = responseEnum.getResult();
        this.code = responseEnum.getCode();
        this.message = I18nUtil.getMessage(responseEnum.getMessage(), args);
    }

    // 返回结果包含数据，返回信息包含变量
    public ResponseVO(ResponseEnum responseEnum, T data, Object[] args) {
        this.result = responseEnum.getResult();
        this.code = responseEnum.getCode();
        this.message = I18nUtil.getMessage(responseEnum.getMessage(), args);
        this.data = data;
    }

    // 自定义返回结果构造器
    public ResponseVO(Boolean result, Integer code, String message) {
        this.result = result;
        this.code = code;
        this.message = I18nUtil.getMessage(message);
    }

}