package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.dao.MessageForm;
import com.dado.quanlytailieu.dto.MessageInfoDTO;
import com.dado.quanlytailieu.entity.MessageEntity;
import com.dado.quanlytailieu.entity.RoomChatEntity;
import com.dado.quanlytailieu.repository.MessageRepository;
import com.dado.quanlytailieu.repository.RoomChatRepository;
import com.dado.quanlytailieu.repository.UserRepository;
import com.dado.quanlytailieu.service.MessageService;
import com.dado.quanlytailieu.service.RoomChatService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/messages")
public class MessageRestController {
    private MessageService messageService;
    private RoomChatService roomChatService;
    private MessageRepository messageRepository;
    private RoomChatRepository roomChatRepository;
    private UserRepository userRepository;

    @PutMapping("/room-chat")
    public ResponseEntity<List<MessageInfoDTO>> findAllByRoomChat(@RequestBody RoomChatEntity roomChat){
        return new ResponseEntity<>(messageService.findAllByRoomChat(roomChat), HttpStatus.ACCEPTED);
    }
    @PostMapping
    public ResponseEntity<MessageEntity> save(@RequestBody MessageForm messageForm){
        MessageEntity message = new MessageEntity();
        message.setCreatedAt(LocalDateTime.now());
        message.setSender(userRepository.findById(messageForm.getUserId()).orElseThrow());
        message.setContent(messageForm.getContent());
        message.setRoomChat(roomChatRepository.findById(messageForm.getRoomChatId()).orElseThrow());
        return new ResponseEntity<>(messageService.save(message), HttpStatus.CREATED);
    }
    @PutMapping("/read/{roomChatId}")
    public ResponseEntity<MessageEntity> read(@RequestBody MessageEntity message, @PathVariable Long roomChatId){
        Optional<RoomChatEntity> roomChat = roomChatService.findById(roomChatId);
        if (!roomChat.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        message.setRoomChat(roomChat.get());
        message.setStatus(true);
        return new ResponseEntity<>(messageService.save(message), HttpStatus.ACCEPTED);
    }
}
