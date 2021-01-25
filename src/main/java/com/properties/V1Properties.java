package com.properties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019/1/28 3:40 PM
 */
@Data
@Component
@ConfigurationProperties(prefix = "v1")
public class V1Properties {

    @ApiModelProperty(value = "用户资产查询地址", required = true)
    private String accounts;

}

