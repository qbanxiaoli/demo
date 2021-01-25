package com.model.entity;

import com.util.JwtUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * @author terminus
 * @description
 * @create 2019/12/11 7:30 下午
 */
@Getter
@Setter
@MappedSuperclass
@ApiModel(value = "操作人模型")
public class CreatorEntity extends GmtEntity {

    @CreatedBy
    @Column(nullable = false, columnDefinition = "varchar(50) COMMENT '创建人'")
    @ApiModelProperty(value = "创建人", required = true)
    private String createdBy;

    @LastModifiedBy
    @Column(nullable = false, columnDefinition = "varchar(50) COMMENT '修改人'")
    @ApiModelProperty(value = "修改人", required = true)
    private String modifiedBy;

    /**
     * 注解说明：
     *
     * @PrePersist 和 @PostPersist 事件的触发由保存实体引起。
     * @PrePersist 事件在调用EntityManager.persist()方法后立刻发生，级联保存也会发生此事件，此时的数据还没有真实插入进数据库。
     * @PostPersist 事件在数据已经插入进数据库后发生。
     */
    @PrePersist
    public void prePersist() {
        this.setCreatedBy(JwtUtil.getUsername());
        this.setModifiedBy(JwtUtil.getUsername());
    }

    /**
     * 注解说明：
     *
     * @PreUpdate 和 @PostUpdate 事件的触发由更新实体引起。
     * @PreUpdate 事件在实体的状态同步到数据库之前触发，此时的数据还没有真实更新到数据库。
     * @PostUpdate 事件在实体的状态同步到数据库后触发，同步在事务提交时发生。
     */
    @PreUpdate
    public void preUpdate() {
        this.setModifiedBy(JwtUtil.getUsername());
    }

}
