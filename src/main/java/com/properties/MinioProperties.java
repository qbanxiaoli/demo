package com.properties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Q版小李
 * @description
 * @create 2021/3/16 15:21
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    @ApiModelProperty(value = "连接地址", required = true)
    private String endpoint;

    @ApiModelProperty(value = "桶名称", required = true)
    private String bucketName;

    @ApiModelProperty(value = "用户名", required = true)
    private String accessKey;

    @ApiModelProperty(value = "密码", required = true)
    private String secretKey;

    @ApiModelProperty(value = "访问地址", required = true)
    private String webServerUrl;

    @ApiModelProperty(value = "是否启用(true->启用，false->不启用)", required = true)
    private Boolean property;

}
