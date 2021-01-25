package com.model.entity;

import com.enums.TrendEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-01 22:33
 */

@Getter
@Setter
@Entity
@Table(name = "market_info", indexes = @Index(name = "idx_period", columnList = "period", unique = true))
@ApiModel(value = "市场行情信息")
public class MarketInfo extends GmtEntity {

    @Column(nullable = false, columnDefinition = "bigint COMMENT '调整为新加坡时间的时间戳，单位秒，并以此作为此K线柱的id'")
    @ApiModelProperty(value = "调整为新加坡时间的时间戳，单位秒，并以此作为此K线柱的id", required = true)
    private Long klineId;

    @Column(nullable = false, columnDefinition = "varchar(10) COMMENT '时间粒度'")
    @ApiModelProperty(value = "时间粒度", required = true)
    private String period;

    @Column(nullable = false, columnDefinition = "decimal(10,2) COMMENT '以基础币种计量的交易量'")
    @ApiModelProperty(value = "以基础币种计量的交易量", required = true)
    private BigDecimal amount;

    @Column(nullable = false, columnDefinition = "decimal(10,2) COMMENT '本阶段开盘价'")
    @ApiModelProperty(value = "本阶段开盘价", required = true)
    private BigDecimal open;

    @Column(nullable = false, columnDefinition = "decimal(10,2) COMMENT '本阶段收盘价'")
    @ApiModelProperty(value = "本阶段收盘价", required = true)
    private BigDecimal close;

    @Column(nullable = false, columnDefinition = "decimal(10,2) COMMENT '本阶段最低价'")
    @ApiModelProperty(value = "本阶段最低价", required = true)
    private BigDecimal low;

    @Column(nullable = false, columnDefinition = "decimal(10,2) COMMENT '本阶段最高价'")
    @ApiModelProperty(value = "本阶段最高价", required = true)
    private BigDecimal high;

    @Column(nullable = false, columnDefinition = "decimal(10,2) COMMENT '布林轨下轨价格'")
    @ApiModelProperty(value = "布林轨下轨价格", required = true)
    private BigDecimal lowerTrack;

    @Column(nullable = false, columnDefinition = "decimal(10,2) COMMENT '布林轨中轨价格'")
    @ApiModelProperty(value = "布林轨中轨价格", required = true)
    private BigDecimal middleTrack;

    @Column(nullable = false, columnDefinition = "decimal(10,2) COMMENT '布林轨上轨价格'")
    @ApiModelProperty(value = "布林轨上轨价格", required = true)
    private BigDecimal upperTrack;

    @Column(nullable = false, columnDefinition = "tinyint(1) COMMENT 'K线趋势(0->趋势未知，1->超跌反弹，上升趋势，2->触顶回落，下降趋势)'")
    @ApiModelProperty(value = "K线趋势(0->趋势未知，1->超跌反弹，上升趋势，2->触顶回落，下降趋势)", required = true)
    private Integer trend = TrendEnum.UN_KNOW.getValue();

}