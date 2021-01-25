package com.exception;

import com.alibaba.fastjson.JSON;
import com.enums.ResponseEnum;
import com.model.vo.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-16 21:47
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        switch (e.getMessage()) {
            case "Full authentication is required to access this resource":
                response.getWriter().write(JSON.toJSONString(new ResponseVO<>(ResponseEnum.USER_NOT_LOGIN)));
                break;
            case "Bad credentials":
                response.getWriter().write(JSON.toJSONString(new ResponseVO<>(ResponseEnum.CLIENT_ERROR)));
                break;
            case "Cannot convert access token to JSON":
                response.getWriter().write(JSON.toJSONString(new ResponseVO<>(ResponseEnum.TOKEN_ERROR)));
                break;
            default:
                response.getWriter().write(JSON.toJSONString(new ResponseVO<>(ResponseEnum.TOKEN_EXPIRED)));
                break;
        }
    }

}
