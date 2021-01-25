package com.controller;

import com.model.dto.QueryDTO;
import com.util.JsonUtil;
import com.util.OAuth2Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-18 03:03
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysFileInfoQueryControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy filterChainProxy;

    private MockMvc mockMvc;

    private OAuth2AccessToken oAuth2AccessToken;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(filterChainProxy).build();
        this.oAuth2AccessToken = OAuth2Util.loginByPassword( "admin", "123456");
    }

    @Test
    public void getFileUrl() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/file/url/1")
                .header(HttpHeaders.AUTHORIZATION, OAuth2AccessToken.BEARER_TYPE.concat(" ").concat(oAuth2AccessToken.getValue())));
        resultActions.andExpect(status().isOk())
                .andReturn().getResponse()
                .setCharacterEncoding(StandardCharsets.UTF_8.name());
        resultActions.andDo(print());
    }

    @Test
    public void pagingFile() throws Exception {
        QueryDTO queryDTO = new QueryDTO();
        log.info("请求参数：{}", JsonUtil.toJsonString(queryDTO));
        ResultActions resultActions = this.mockMvc.perform(post("/file/paging")
                .header(HttpHeaders.AUTHORIZATION, OAuth2AccessToken.BEARER_TYPE.concat(" ").concat(oAuth2AccessToken.getValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJsonBytes(queryDTO)));
        resultActions.andExpect(status().isOk())
                .andReturn().getResponse()
                .setCharacterEncoding(StandardCharsets.UTF_8.name());
        resultActions.andDo(print());
    }

}
