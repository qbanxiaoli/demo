package com.controller;

import com.model.dto.UserDTO;
import com.enums.ResponseEnum;
import com.model.dto.CaptchaFormDTO;
import com.model.dto.LoginFormDTO;
import com.model.dto.RegisterFormDTO;
import com.model.vo.ResponseVO;
import com.model.vo.UserVO;
import com.service.UserService;
import com.util.FileUtil;
import com.util.MailUtil;
import com.util.OAuth2Util;
import com.util.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-12-04 10:51
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "用户模块")
@RequestMapping("/user")
public class UserManageController {

    private final UserService userService;

    @ApiOperation("发送验证码")
    @PostMapping("/captcha/send")
    public ResponseVO sendCaptcha(@ApiParam(name = "captchaFormDTO", value = "发送验证码数据请求模型", required = true)
                                  @Validated @RequestBody CaptchaFormDTO captchaFormDTO) {
        if (captchaFormDTO.getType().equals(1)) {
            MailUtil.sendMail("验证码", captchaFormDTO.getUsername(), "您的验证码为" + RandomUtil.generateString(6));
        }
        return new ResponseVO<>(ResponseEnum.SUCCESS);
    }

    @ApiOperation("刷新token")
    @PutMapping("/token/refresh")
    public ResponseVO<OAuth2AccessToken> refreshToken(@ApiParam(name = "refreshToken", value = "刷新用的token", required = true)
                                                      @RequestParam("refreshToken") String refreshToken) {
        OAuth2AccessToken oAuth2AccessToken = OAuth2Util.refreshToken(refreshToken);
        if (StringUtils.isBlank(oAuth2AccessToken.getValue())) {
            return new ResponseVO<>(ResponseEnum.FAILURE_VARIABLE, new Object[]{oAuth2AccessToken.getAdditionalInformation().get("error_description")});
        }
        return new ResponseVO<>(ResponseEnum.SUCCESS, oAuth2AccessToken);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseVO<OAuth2AccessToken> login(@ApiParam(name = "loginFormDTO", value = "用户登录数据传输模型", required = true)
                                               @Validated @RequestBody LoginFormDTO loginFormDTO) {
        OAuth2AccessToken oAuth2AccessToken = OAuth2Util.loginByPassword(loginFormDTO.getUsername(), loginFormDTO.getPassword());
        if (StringUtils.isBlank(oAuth2AccessToken.getValue())) {
            if (oAuth2AccessToken.getAdditionalInformation().get("message") != null) {
                return new ResponseVO<>(ResponseEnum.FAILURE_VARIABLE, new Object[]{oAuth2AccessToken.getAdditionalInformation().get("message")});
            }
            return new ResponseVO<>(ResponseEnum.FAILURE_VARIABLE, new Object[]{oAuth2AccessToken.getAdditionalInformation().get("error_description")});
        }
        return new ResponseVO<>(ResponseEnum.SUCCESS, oAuth2AccessToken);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public ResponseVO register(@ApiParam(name = "registerFormDTO", value = "用户注册数据传输模型", required = true)
                               @Validated @RequestBody RegisterFormDTO registerFormDTO) {
        userService.register(registerFormDTO);
        return new ResponseVO<>(ResponseEnum.SUCCESS);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation("下载excel导入模版")
    @GetMapping("/template/excel/import/download")
    public void downloadExcelImportTemplate() {
        List<UserDTO> userDTOList = new ArrayList<>();
        FileUtil.exportToExcel(userDTOList, UserDTO.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation("下载csv导入模版")
    @GetMapping("/template/csv/import/download")
    public void downloadCsvImportTemplate() {
        List<UserDTO> userDTOList = new ArrayList<>();
        FileUtil.exportToCSV(userDTOList, UserDTO.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation("从csv文件导入用户信息")
    @PostMapping("/csv/import")
    public ResponseVO<List<UserDTO>> importUserInfoFromCsv(@ApiParam(name = "file", value = "待上传csv文件", required = true)
                                                           @RequestPart(name = "file") MultipartFile multipartFile) {
        List<UserDTO> userDTOList = FileUtil.importFromCsv(multipartFile, UserDTO.class);
        return new ResponseVO<>(ResponseEnum.SUCCESS, userDTOList);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation("从excel文件导入用户信息")
    @PostMapping("/excel/import")
    public ResponseVO<List<UserDTO>> importUserInfoFromExcel(@ApiParam(name = "file", value = "待上传excel文件", required = true)
                                                             @RequestPart(name = "file") MultipartFile multipartFile) {
        List<UserDTO> userDTOList = FileUtil.importFromExcel(multipartFile, UserDTO.class);
        return new ResponseVO<>(ResponseEnum.SUCCESS, userDTOList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("导出用户信息到excel文件")
    @GetMapping("/excel/export")
    public void exportUserInfoToExcel() {
        List<UserVO> userVOList = userService.getUserInfoList();
        FileUtil.exportToExcel(userVOList, UserVO.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("导出用户信息到csv文件")
    @GetMapping("/csv/export")
    public void exportUserInfoToCSV() {
        List<UserVO> userVOList = userService.getUserInfoList();
        FileUtil.exportToCSV(userVOList, UserVO.class);
    }

}
