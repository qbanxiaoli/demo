package com.controller;

import com.model.dto.SysFileInfoFromDTO;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
public class SysFileInfoManageControllerTest {

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
    public void uploadImage() throws Exception {
        String filePath = this.getClass().getResource("/picture.jpg").getPath();
        File file = new File(filePath);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file", //文件名
                file.getName(), //originalName 相当于上传文件在客户机上的文件名
                MediaType.IMAGE_JPEG_VALUE, //文件类型
                new FileInputStream(file) //文件流
        );
        SysFileInfoFromDTO sysFileInfoFromDTO = new SysFileInfoFromDTO();
        sysFileInfoFromDTO.setThumbnail(true);
        log.info("请求参数：{}", JsonUtil.toJsonString(sysFileInfoFromDTO));
        ResultActions resultActions = this.mockMvc.perform(multipart("/file/image/upload")
                .file(mockMultipartFile)
                .header(HttpHeaders.AUTHORIZATION, OAuth2AccessToken.BEARER_TYPE.concat(" ").concat(oAuth2AccessToken.getValue()))
                .content(JsonUtil.toJsonBytes(sysFileInfoFromDTO)));
        resultActions.andExpect(status().isOk())
                .andReturn().getResponse()
                .setCharacterEncoding(StandardCharsets.UTF_8.name());
        resultActions.andDo(print());
    }

    @Test
    public void uploadFile() throws Exception {
        String filePath = this.getClass().getResource("/picture.jpg").getPath();
        File file = new File(filePath);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file", //文件名
                file.getName(), //originalName 相当于上传文件在客户机上的文件名
                MediaType.IMAGE_JPEG_VALUE, //文件类型
                new FileInputStream(file) //文件流
        );
        ResultActions resultActions = this.mockMvc.perform(multipart("/file/upload")
                .file(mockMultipartFile)
                .header(HttpHeaders.AUTHORIZATION, OAuth2AccessToken.BEARER_TYPE.concat(" ").concat(oAuth2AccessToken.getValue())));
        resultActions.andExpect(status().isOk())
                .andReturn().getResponse()
                .setCharacterEncoding(StandardCharsets.UTF_8.name());
        resultActions.andDo(print());
    }

    @Test
    public void downloadFile() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/file/download/1")
                .header(HttpHeaders.AUTHORIZATION, OAuth2AccessToken.BEARER_TYPE.concat(" ").concat(oAuth2AccessToken.getValue())));
        resultActions.andExpect(status().isOk())
                .andReturn().getResponse()
                .setCharacterEncoding(StandardCharsets.UTF_8.name());
        resultActions.andDo(print());
    }

    @Test
    public void updateImage() throws Exception {
        String filePath = this.getClass().getResource("/picture.jpg").getPath();
        File file = new File(filePath);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file", //文件名
                file.getName(), //originalName 相当于上传文件在客户机上的文件名
                MediaType.IMAGE_JPEG_VALUE, //文件类型
                new FileInputStream(file) //文件流
        );
        SysFileInfoFromDTO sysFileInfoFromDTO = new SysFileInfoFromDTO();
        sysFileInfoFromDTO.setThumbnail(true);
        log.info("请求参数：{}", JsonUtil.toJsonString(sysFileInfoFromDTO));
        ResultActions resultActions = this.mockMvc.perform(multipart("/file/image/update/1")
                .file(mockMultipartFile)
                .header(HttpHeaders.AUTHORIZATION, OAuth2AccessToken.BEARER_TYPE.concat(" ").concat(oAuth2AccessToken.getValue()))
                .content(JsonUtil.toJsonBytes(sysFileInfoFromDTO)));
        resultActions.andExpect(status().isOk())
                .andReturn().getResponse()
                .setCharacterEncoding(StandardCharsets.UTF_8.name());
        resultActions.andDo(print());
    }

    @Test
    public void updateFile() throws Exception {
        String filePath = this.getClass().getResource("/picture.jpg").getPath();
        File file = new File(filePath);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file", //文件名
                file.getName(), //originalName 相当于上传文件在客户机上的文件名
                MediaType.IMAGE_JPEG_VALUE, //文件类型
                new FileInputStream(file) //文件流
        );
        ResultActions resultActions = this.mockMvc.perform(multipart("/file/update/1")
                .file(mockMultipartFile)
                .header(HttpHeaders.AUTHORIZATION, OAuth2AccessToken.BEARER_TYPE.concat(" ").concat(oAuth2AccessToken.getValue())));
        resultActions.andExpect(status().isOk())
                .andReturn().getResponse()
                .setCharacterEncoding(StandardCharsets.UTF_8.name());
        resultActions.andDo(print());
    }

    @Test
    public void deleteFile() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(delete("/file/delete/1")
                .header(HttpHeaders.AUTHORIZATION, OAuth2AccessToken.BEARER_TYPE.concat(" ").concat(oAuth2AccessToken.getValue())));
        resultActions.andExpect(status().isOk())
                .andReturn().getResponse()
                .setCharacterEncoding(StandardCharsets.UTF_8.name());
        resultActions.andDo(print());
    }

}
