package com.properties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Q版小李
 * @description
 * @create 2019/1/18 17:06
 */
@Data
@Component
@ConfigurationProperties(prefix = "proxy")
public class ProxyProperties {

    @ApiModelProperty(value = "代理地址", required = true)
    private String host = "127.0.0.1";

    @ApiModelProperty(value = "代理端口", required = true)
    private Integer port = 1080;

}
