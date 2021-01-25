package com.properties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-03-07 16:56
 */
@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    @ApiModelProperty(value = "是否启用swagger文档", required = true)
    private Boolean show = true;

}
