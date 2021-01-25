package com.websocket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-04-01 13:41
 */
@Slf4j
@Controller
@AllArgsConstructor
public class WebSocket {

    private final SimpUserRegistry userRegistry;

    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/websocket")
    public String index() {
        return "/index";
    }

    //广播推送消息
    @Scheduled(fixedRate = 3000)
    public void sendTopicMessage() {
        int i = 1;
        for (SimpUser user : userRegistry.getUsers()) {
            log.info("用户" + i++ + "---" + user);
            messagingTemplate.convertAndSendToUser(user.getName(), "/queue/message", "Hello World!");
        }
    }

}