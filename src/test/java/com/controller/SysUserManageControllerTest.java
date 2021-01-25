package com.controller;

import com.model.dto.LoginFormDTO;
import com.model.dto.RegisterFormDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-12-08 21:44
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserManageControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy filterChainProxy;

    private MockMvc mockMvc;

    private OAuth2AccessToken oAuth2AccessToken;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(filterChainProxy).build();
        this.oAuth2AccessToken = OAuth2Util.loginByPassword("admin", "123456");
    }

    @Test
    public void login() throws Exception {
        LoginFormDTO loginFormDTO = new LoginFormDTO();
        loginFormDTO.setUsername("admin");
        loginFormDTO.setPassword("123456");
        log.info("请求参数：{}", JsonUtil.toJsonString(loginFormDTO));
        ResultActions resultActions = this.mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJsonBytes(loginFormDTO)));
        resultActions.andExpect(status().isOk())
                .andReturn().getResponse()
                .setCharacterEncoding(StandardCharsets.UTF_8.name());
        resultActions.andDo(print());
    }

    @Test
    public void register() throws Exception {
        RegisterFormDTO registerFormDTO = new RegisterFormDTO();
        registerFormDTO.setUsername("qbanxiaoli");
        registerFormDTO.setPassword("123456");
        registerFormDTO.setCode("1234");
        log.info("请求参数：{}", JsonUtil.toJsonString(registerFormDTO));
        ResultActions resultActions = this.mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJsonBytes(registerFormDTO)));
        resultActions.andExpect(status().isOk())
                .andReturn().getResponse()
                .setCharacterEncoding(StandardCharsets.UTF_8.name());
        resultActions.andDo(print());
    }

    @Test
    public void exportUserInfoToExcel() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/user/excel/export")
                .header(HttpHeaders.AUTHORIZATION, OAuth2AccessToken.BEARER_TYPE.concat(" ").concat(oAuth2AccessToken.getValue())));
        resultActions.andExpect(status().isOk())
                .andReturn().getResponse()
                .setCharacterEncoding(StandardCharsets.UTF_8.name());
        resultActions.andDo(print());
    }

    @Test
    public void exportUserINfoToCsv() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/user/csv/export")
                .header(HttpHeaders.AUTHORIZATION, OAuth2AccessToken.BEARER_TYPE.concat(" ").concat(oAuth2AccessToken.getValue())));
        resultActions.andExpect(status().isOk())
                .andReturn().getResponse()
                .setCharacterEncoding(StandardCharsets.UTF_8.name());
        resultActions.andDo(print());
    }

}
