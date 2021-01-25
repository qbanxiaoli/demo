package com.properties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Q版小李
 * @description
 * @create 2019/1/18 18:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "headers")
public class HeadersProperties {

    @ApiModelProperty(value = "请求头userAgent", required = true)
    private String userAgent;

}
