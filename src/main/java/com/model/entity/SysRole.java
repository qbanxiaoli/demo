package com.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/12/12 15:40
 */
@Getter
@Setter
@Entity
@Table(name = "sys_role", indexes = @Index(name = "idx_authority", columnList = "authority", unique = true))
@ApiModel(value = "用户角色")
public class SysRole extends IdEntity implements GrantedAuthority {

    @Column(nullable = false, columnDefinition = "varchar(20) COMMENT '角色名称'")
    @ApiModelProperty(value = "角色名称", required = true)
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    @ApiModelProperty(value = "用户列表", required = true)
    private List<SysUser> sysUsers;

}
