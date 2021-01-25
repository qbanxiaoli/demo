package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.properties.ContractProperties;
import com.properties.HuobiProperties;
import com.enums.MarketEnum;
import com.model.vo.EliteAccountRatioVO;
import com.model.vo.ElitePositionRatioVO;
import com.model.vo.ListVO;
import com.model.vo.ResultVO;
import com.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-01-24 13:24
 */
@Service
@AllArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final HuobiProperties huobiProperties;

    private final ContractProperties contractProperties;

    private final RestTemplate restTemplate;

    @Override
    public ResultVO<ListVO<List<EliteAccountRatioVO>>> getEliteAccountRatio(String symbol, String period) {
        URI uri = UriComponentsBuilder.fromHttpUrl(huobiProperties.getContractUrl() + contractProperties.getEliteAccountRatio())
                .queryParam(MarketEnum.SYMBOL.getValue(), symbol)
                .queryParam(MarketEnum.PERIOD.getValue(), period)
                .build().encode().toUri();
        String eliteAccountRatio = restTemplate.getForObject(uri, String.class);
        return JSON.parseObject(eliteAccountRatio, new TypeReference<ResultVO<ListVO<List<EliteAccountRatioVO>>>>() {
        });
    }

    @Override
    public ResultVO<ListVO<List<ElitePositionRatioVO>>> getElitePositionRatio(String symbol, String period) {
        URI uri = UriComponentsBuilder.fromHttpUrl(huobiProperties.getContractUrl() + contractProperties.getElitePositionRatio())
                .queryParam(MarketEnum.SYMBOL.getValue(), symbol)
                .queryParam(MarketEnum.PERIOD.getValue(), period)
                .build().encode().toUri();
        String elitePositionRatio = restTemplate.getForObject(uri, String.class);
        return JSON.parseObject(elitePositionRatio, new TypeReference<ResultVO<ListVO<List<ElitePositionRatioVO>>>>() {
        });
    }

}
