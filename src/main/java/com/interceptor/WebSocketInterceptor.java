package com.interceptor;
import com.alibaba.fastjson.JSON;
import com.enums.ResponseEnum;
import com.model.vo.JwtVO;
import com.util.I18nUtil;
import com.util.JsonUtil;
import org.apache.http.auth.BasicUserPrincipal;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author qbanxiaoli
 * @description webSocket用户信息拦截
 * @create 2020-03-25 16:22
 */
public class WebSocketInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(Objects.requireNonNull(accessor).getCommand())) {
            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
            if (raw instanceof Map) {
                // 这里就是token
                Object name = JsonUtil.parseMap(JsonUtil.toJsonString(raw)).get(HttpHeaders.AUTHORIZATION);
                if (Objects.isNull(name)) {
                    throw new MessagingException(I18nUtil.getMessage(ResponseEnum.TOKEN_NOT_NULL.name()));
                }
                if (name instanceof List) {
                    // 设置当前访问器的认证用户
                    String authorization = JsonUtil.parseList(JsonUtil.toJsonString(name)).get(0).toString();
                    if (!authorization.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase())) {
                        throw new MessagingException(I18nUtil.getMessage(ResponseEnum.TOKEN_ERROR.name()));
                    }
                    String token = authorization.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim();
                    Jwt jwt = JwtHelper.decode(token);
                    JwtVO jwtVO = JSON.parseObject(jwt.getClaims(), JwtVO.class);
                    accessor.setUser(new BasicUserPrincipal(jwtVO.getUsername()));
                }
            }
        }
        return message;
    }

}
