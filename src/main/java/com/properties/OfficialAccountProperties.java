package com.properties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-28 11:10
 */
@Data
@Component
@ConfigurationProperties(prefix = "official-account")
public class OfficialAccountProperties {

    @ApiModelProperty(value = "获取access-token", required = true)
    private String accessTokenUrl;

    @ApiModelProperty(value = "获取关注者列表", required = true)
    private String userList;

    @ApiModelProperty(value = "获取用户基本信息", required = true)
    private String userInfo;

}
