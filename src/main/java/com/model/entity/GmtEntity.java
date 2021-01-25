package com.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Q版小李
 * @description
 * @create 2018/7/30 2:06
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "时间模型")
public class GmtEntity extends IdEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "datetime COMMENT '创建时间'")
    @ApiModelProperty(value = "创建时间", required = true)
    private Date gmtCreated;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "datetime COMMENT '修改时间'")
    @ApiModelProperty(value = "修改时间", required = true)
    private Date gmtModified;

}
