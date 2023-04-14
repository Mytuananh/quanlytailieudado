package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.dao.MessageRequest;
import com.dado.quanlytailieu.entity.MessageEntity;
import com.dado.quanlytailieu.repository.MessageRepository;
import com.dado.quanlytailieu.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {
    private MessageRepository messageRepository;

    private SimpMessagingTemplate simpMessagingTemplate;

    private ChatService chatService;

    @PostMapping("/getMessages")
    public List<MessageEntity> getMessages(@RequestBody String chat) {
        // TODO: remove senderId and using JWT to get senderId
        return chatService.getMessages(chat);
    }

    @PostMapping
    public MessageEntity sendMessage(@RequestBody MessageEntity messageEntity) {
        messageEntity.setCreatedAt(LocalDateTime.now());
        return messageRepository.save(messageEntity);
    }

    @MessageMapping("/chat/{to}")   // to = name canale
    public void sendMessage(@PathVariable String to, @PathVariable String from, MessageRequest message) {
        System.out.println("handling send message: " + message + " to: " + to);
        MessageEntity mes = new MessageEntity();
        mes.setSenderId(message.getSenderId());
        mes.setChatId(chatService.createAndOrGetChat(to));
        mes.setContent(message.getContent());
        messageRepository.save(mes);
        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
    }
}

