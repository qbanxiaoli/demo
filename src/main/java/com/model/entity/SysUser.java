package com.model.entity;

import com.aop.annotation.Security;
import com.strategy.enums.SecretTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/12/11 19:25
 */
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "sys_user", indexes = @Index(name = "idx_username", columnList = "username", unique = true))
@ApiModel(value = "用户信息")
public class SysUser extends GmtEntity implements UserDetails {

    @Column(nullable = false, columnDefinition = "varchar(50) COMMENT '用户名'")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @Column(columnDefinition = "varchar(100) COMMENT '密码'")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @Column(columnDefinition = "varchar(50) COMMENT '昵称'")
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @Security(type = SecretTypeEnum.DEFAULT)
    @Column(columnDefinition = "char(11) COMMENT '手机号'")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @Column(columnDefinition = "varchar(20) COMMENT '邮箱'")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Column(columnDefinition = "bigint COMMENT '头像id'")
    @ApiModelProperty(value = "头像id")
    private Long avatarId;

    @Column(columnDefinition = "tinyint(1) COMMENT '性别(1->男，2->女)'")
    @ApiModelProperty(value = "性别")
    private Integer sex;

    @Column(columnDefinition = "datetime COMMENT '生日'")
    @ApiModelProperty(value = "生日")
    private Date birthday;

    @Column(nullable = false, columnDefinition = "tinyint(1) DEFAULT '1' COMMENT '账户是否过期(0->过期，1->未过期)'")
    @ApiModelProperty(value = "账户是否过期(false->过期，true->未过期)", required = true)
    private boolean accountNonExpired = true;

    @Column(nullable = false, columnDefinition = "tinyint(1) DEFAULT '1' COMMENT '账户是否锁定(0->锁定，1->未锁定)'")
    @ApiModelProperty(value = "账户是否锁定(false->锁定，true->未锁定)", required = true)
    private boolean accountNonLocked = true;

    @Column(nullable = false, columnDefinition = "tinyint(1) DEFAULT '1' COMMENT '凭证是否过期(0->过期，1->未过期)'")
    @ApiModelProperty(value = "凭证是否过期(false->过期，true->未过期)", required = true)
    private boolean credentialsNonExpired = true;

    @Column(nullable = false, columnDefinition = "tinyint(1) DEFAULT '1' COMMENT '账户是否有效(0->无效，1->有效)'")
    @ApiModelProperty(value = "账户是否有效(false->无效，true->有效)", required = true)
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ApiModelProperty(value = "角色列表", required = true)
    private List<SysRole> authorities;

}
