package com.util;

import com.enums.ResponseEnum;
import com.model.entity.Account;
import com.properties.HeadersProperties;
import com.dao.repository.AccountRepository;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Q版小李
 * @description
 * @create 2019/1/18 19:51
 */
@Component
public class HttpUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static AccountRepository accountRepository;

    private static HeadersProperties headersProperties;

    public HttpUtil(HeadersProperties headersProperties, AccountRepository accountRepository) {
        HttpUtil.headersProperties = headersProperties;
        HttpUtil.accountRepository = accountRepository;
    }

    public static HttpEntity getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.USER_AGENT, headersProperties.getUserAgent());
        return new HttpEntity<>(headers);
    }

    @SneakyThrows
    public static URI createSignature(String method, String address, UriComponentsBuilder uriComponentsBuilder, MultiValueMap<String, String> params) {
        Account account = accountRepository.findByUserId(JwtUtil.getUserId());
        if (account == null) {
            throw new RuntimeException(ResponseEnum.ACCOUNT_NOT_EXIST.name());
        }
        params.add("AccessKeyId", account.getAccessKeyId());
        params.add("SignatureVersion", "2");
        params.add("SignatureMethod", "HmacSHA256");
        params.add("Timestamp", Instant.now().atOffset(ZoneOffset.UTC).format(formatter));
        uriComponentsBuilder.queryParams(params);
        Object object = FieldUtils.getFieldValue(uriComponentsBuilder, "host");
        // 构造签名
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secKey = new SecretKeySpec(account.getAccessKeySecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSha256.init(secKey);
        StringBuilder sb = new StringBuilder();
        sb.append(method).append('\n')
                .append(object.toString()).append('\n')
                .append(address).append('\n');
        Map<String, String> map = new TreeMap<>(params.toSingleValueMap());
        map.forEach((key, value) -> {
            try {
                sb.append(key).append('=').append(URLEncoder.encode(value, StandardCharsets.UTF_8.name()).replaceAll("\\+", "%20")).append('&');
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        int index = sb.toString().lastIndexOf('&');
        String payload = sb.toString().substring(0, index);
        byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        String actualSign = Base64.getEncoder().encodeToString(hash);
        uriComponentsBuilder.queryParam("Signature", actualSign);
        return uriComponentsBuilder.build().encode().toUri();
    }

}
