package com.model.dto;

import com.aop.annotation.DataDict;
import com.aop.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/12/11 19:25
 */
@Data
@ApiModel(value = "用户信息导入模版")
public class UserDTO {

    @Excel(value = "用户名", type = Excel.TypeEnum.ALL)
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @Excel(value = "昵称", type = Excel.TypeEnum.ALL)
    @ApiModelProperty(value = "昵称", required = true)
    private String nickname;

    @Excel(value = "手机号", type = Excel.TypeEnum.ALL)
    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @Excel(value = "邮箱", type = Excel.TypeEnum.ALL)
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @DataDict(tableName = "oauth_user", dict = "sex")
    @ApiModelProperty(value = "性别字典")
    private String sexDict;

    @Excel(value = "性别", type = Excel.TypeEnum.ALL)
    @ApiModelProperty(value = "性别", required = true)
    private Integer sex;

    @Excel(value = "生日", type = Excel.TypeEnum.ALL)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "生日", required = true)
    private Date birthday;

}
