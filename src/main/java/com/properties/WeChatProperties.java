package com.properties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-28 10:30
 */
@Data
@Component
@ConfigurationProperties(prefix = "we-chat")
public class WeChatProperties {

    @ApiModelProperty(value = "微信公接口调用地址", required = true)
    private String weChatUrl;

    @ApiModelProperty(value = "微信公众号appId", required = true)
    private String appId;

    @ApiModelProperty(value = "微信公众号appSecret", required = true)
    private String appSecret;

}
