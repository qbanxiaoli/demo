package com.properties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Q版小李
 * @description
 * @create 2019/1/18 18:44
 */
@Data
@Component
@ConfigurationProperties(prefix = "huobi")
public class HuobiProperties {

    @ApiModelProperty(value = "火币请求地址", required = true)
    private String url;

    @ApiModelProperty(value = "火币合约请求地址", required = true)
    private String contractUrl;

}
