package com.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.aop.annotation.DataDict;
import com.aop.annotation.Excel;
import com.aop.annotation.Image;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/12/11 19:25
 */
@Data
@ApiModel(value = "用户信息")
public class UserVO {

    @Excel(value = "用户名", type = Excel.TypeEnum.EXPORT)
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @Excel(value = "昵称", type = Excel.TypeEnum.EXPORT)
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @Excel(value = "手机号", type = Excel.TypeEnum.EXPORT)
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @Excel(value = "邮箱", type = Excel.TypeEnum.EXPORT)
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Excel(value = "头像", type = Excel.TypeEnum.EXPORT)
    @Image(value = "avatarId")
    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "头像id")
    private Long avatarId;

    @DataDict(tableName = "sys_user", dict = "sex")
    @ApiModelProperty(value = "性别字典")
    private String sexDict;

    @Excel(value = "性别", type = Excel.TypeEnum.EXPORT)
    @ApiModelProperty(value = "性别")
    private Integer sex;

    @Excel(value = "生日", type = Excel.TypeEnum.EXPORT)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "账户是否过期(false->过期，true->未过期)", required = true)
    private boolean accountNonExpired;

    @ApiModelProperty(value = "账户是否锁定(false->锁定，true->未锁定)", required = true)
    private boolean accountNonLocked;

    @ApiModelProperty(value = "凭证是否过期(false->过期，true->未过期)", required = true)
    private boolean credentialsNonExpired;

    @ApiModelProperty(value = "账户是否有效(false->无效，true->有效)", required = true)
    private boolean enabled;

}
