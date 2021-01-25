package com.exception;

import com.enums.ResponseEnum;
import com.util.I18nUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/12/19 5:26 PM
 */
@Slf4j
@Component
@AllArgsConstructor
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        if (e.getMessage().equals("Bad credentials")) {
            return ResponseEntity.status(HttpStatus.OK).body(new OAuth2Exception(I18nUtil.getMessage(ResponseEnum.PASSWORD_ERROR.getMessage())));
        } else if (e.getMessage().equals("Cannot convert access token to JSON")) {
            return ResponseEntity.status(HttpStatus.OK).body(new OAuth2Exception(I18nUtil.getMessage(ResponseEnum.TOKEN_ERROR.getMessage())));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new OAuth2Exception(I18nUtil.getMessage(e.getMessage())));
        }
    }

}