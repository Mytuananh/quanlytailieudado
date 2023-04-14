package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.entity.MessageEntity;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
public class WebSocketController {

    private SimpMessagingTemplate messagingTemplate;

//    @MessageMapping("/chat")
//    public void sendMessage(@Payload MessageEntity messageEntity) {
//        messageEntity.setCreatedAt(LocalDateTime.now());
//        messagingTemplate.convertAndSend("/topic/" + messageEntity.getReceiverId(), messageEntity);
//    }
}

