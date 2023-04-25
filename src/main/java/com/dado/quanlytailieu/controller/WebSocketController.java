package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.dao.MessageForm;
import com.dado.quanlytailieu.dao.NotificationForm;
import com.dado.quanlytailieu.entity.MessageEntity;
import com.dado.quanlytailieu.entity.NotificationEntity;
import com.dado.quanlytailieu.repository.RoomChatRepository;
import com.dado.quanlytailieu.repository.UserRepository;
import com.dado.quanlytailieu.service.MessageService;
import com.dado.quanlytailieu.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
@CrossOrigin("*")
public class WebSocketController {

    private SimpMessagingTemplate messagingTemplate;
    private MessageService messageService;
    private NotificationService notificationService;
    private UserRepository userRepository;
    private RoomChatRepository roomChatRepository;

    @MessageMapping("/messages/{roomId}")
    @SendTo("/topic/messages/{roomId}")
    public MessageEntity save(MessageForm messageForm){
        MessageEntity message = new MessageEntity();
        message.setCreatedAt(LocalDateTime.now());
        message.setSender(userRepository.findById(messageForm.getUserId()).orElseThrow());
        message.setContent(messageForm.getContent());
        message.setRoomChat(roomChatRepository.findById(messageForm.getRoomChatId()).orElseThrow());
        message.setStatus(false);
        return messageService.save(message);
    }
    @MessageMapping("/notifications/{username}")
    @SendTo("/topic/notifications/{username}")
    public NotificationEntity saveNotification(NotificationForm notificationForm){
        NotificationEntity notification = new NotificationEntity();
        notification.setUrl(notificationForm.getUrl());
        notification.setContent(notificationForm.getContent());
        notification.setStatus(true);
        notification.setUser(notificationForm.getUser());
        return notificationService.save(notification);
    }
}

