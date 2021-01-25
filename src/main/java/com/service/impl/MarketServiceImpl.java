package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.properties.HuobiProperties;
import com.properties.MarketProperties;
import com.enums.MarketEnum;
import com.model.dto.MarketDepthQueryDTO;
import com.model.vo.DepthVO;
import com.model.vo.KlineVO;
import com.model.vo.MergedVO;
import com.model.vo.ResultVO;
import com.service.MarketService;
import com.util.HttpUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author Q版小李
 * @description 市场行情
 * @create 2019/1/18 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class MarketServiceImpl implements MarketService {

    private final MarketProperties marketProperties;

    private final HuobiProperties huobiProperties;

    private final RestTemplate restTemplate;

    @Override
    public ResultVO<List<KlineVO>> getKlineHistory(String symbol, String period, Integer size) {
        URI uri = UriComponentsBuilder.fromHttpUrl(huobiProperties.getUrl() + marketProperties.getKline())
                .queryParam(MarketEnum.SYMBOL.getValue(), symbol)
                .queryParam(MarketEnum.PERIOD.getValue(), period)
                .queryParam(MarketEnum.SIZE.getValue(), String.valueOf(size))
                .build().encode().toUri();
        HttpEntity entity = HttpUtil.getHttpEntity();
        String klineHistory = restTemplate.postForObject(uri, entity, String.class);
        return JSON.parseObject(klineHistory, new TypeReference<ResultVO<List<KlineVO>>>() {
        });
    }

    @Override
    public ResultVO<MergedVO> getMergedDetail(String symbol) {
        URI uri = UriComponentsBuilder.fromHttpUrl(huobiProperties.getUrl() + marketProperties.getMerged())
                .queryParam(MarketEnum.SYMBOL.getValue(), symbol)
                .build().encode().toUri();
        HttpEntity entity = HttpUtil.getHttpEntity();
        String mergedDetail = restTemplate.postForObject(uri, entity, String.class);
        return JSON.parseObject(mergedDetail, new TypeReference<ResultVO<MergedVO>>() {
        });
    }

    @Override
    public ResultVO<DepthVO> getMarketDepth(MarketDepthQueryDTO marketDepthQueryDTO) {
        URI uri = UriComponentsBuilder.fromHttpUrl(huobiProperties.getUrl() + marketProperties.getDepth())
                .queryParam(MarketEnum.SYMBOL.getValue(), marketDepthQueryDTO.getSymbol())
                .queryParam(MarketEnum.DEPTH.getValue(), String.valueOf(marketDepthQueryDTO.getDepth()))
                .queryParam(MarketEnum.TYPE.getValue(), marketDepthQueryDTO.getType())
                .build().encode().toUri();
        HttpEntity entity = HttpUtil.getHttpEntity();
        String marketDepth = restTemplate.postForObject(uri, entity, String.class);
        return JSON.parseObject(marketDepth, new TypeReference<ResultVO<DepthVO>>() {
        });
    }

}
