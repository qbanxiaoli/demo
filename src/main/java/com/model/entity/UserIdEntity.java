package com.model.entity;

import com.util.JwtUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019/12/11 7:30 下午
 */
@Getter
@Setter
@MappedSuperclass
@ApiModel(value = "用户关联模型")
public class UserIdEntity extends CreatorEntity {

    @Column(nullable = false, columnDefinition = "bigint COMMENT '用户id'")
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;

    @PrePersist
    public void prePersist() {
        this.setUserId(JwtUtil.getUserId());
    }

}
