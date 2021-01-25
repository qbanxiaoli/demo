package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.properties.HuobiProperties;
import com.properties.V1Properties;
import com.dao.repository.AccountRepository;
import com.enums.ResponseEnum;
import com.model.converter.AccountAssembly;
import com.model.dto.AccountFormDTO;
import com.model.entity.Account;
import com.model.vo.AccountVO;
import com.model.vo.ResultVO;
import com.service.AccountService;
import com.util.HttpUtil;
import com.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019/1/28 3:41 PM
 */
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final V1Properties v1Properties;

    private final HuobiProperties huobiProperties;

    private final RestTemplate restTemplate;

    @Override
    public Account getAccount() {
        return accountRepository.findByUserId(JwtUtil.getUserId());
    }

    @Override
    public ResultVO<List<AccountVO>> getAccountInformation() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(huobiProperties.getUrl() + v1Properties.getAccounts());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        URI uri = HttpUtil.createSignature(HttpMethod.GET.name(), v1Properties.getAccounts(), uriComponentsBuilder, params);
        HttpEntity entity = HttpUtil.getHttpEntity();
        String accountInformation = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();
        return JSON.parseObject(accountInformation, new TypeReference<ResultVO<List<AccountVO>>>() {
        });
    }

    @Override
    public Long addAccount(AccountFormDTO accountFormDTO) {
        Optional<Account> accountOptional = accountRepository.findById(JwtUtil.getUserId());
        if (accountOptional.isPresent()) {
            throw new RuntimeException(ResponseEnum.ACCOUNT_EXIST.name());
        }
        Account account = AccountAssembly.toDomain(accountFormDTO);
        return accountRepository.save(account).getId();
    }

    @Override
    public Long updateAccount(AccountFormDTO accountFormDTO) {
        Account account = accountRepository.findByUserId(JwtUtil.getUserId());
        if (account == null) {
            throw new RuntimeException(ResponseEnum.ACCOUNT_NOT_EXIST.name());
        }
        AccountAssembly.toDomain(account, accountFormDTO);
        return accountRepository.save(account).getId();
    }

}
