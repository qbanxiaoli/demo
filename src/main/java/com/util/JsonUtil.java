package com.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author qbanxiaoli
 * @description Json转换工具类
 * @create 2018/7/3 13:31
 */
@Slf4j
public class JsonUtil {

    @SneakyThrows
    public static byte[] toJsonBytes(Object object) {
        // 使用ObjectMapper来转化对象为Byte数组
        ObjectMapper objectMapper = new ObjectMapper();
        // 配置objectMapper忽略空属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsBytes(object);
    }

    @SneakyThrows
    public static String toJsonString(Object object) {
        // 使用ObjectMapper来转化对象为Json
        ObjectMapper objectMapper = new ObjectMapper();
        // 配置objectMapper忽略空属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(object);
    }

    @SneakyThrows
    public static <T> T parseObject(String json) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<T>() {
        });
    }

    @SneakyThrows
    public static <T> List<T> parseList(String json) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<List<T>>() {
        });
    }

    @SneakyThrows
    public static <K, V> Map<K, V> parseMap(String json) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<Map<K, V>>() {
        });
    }

}