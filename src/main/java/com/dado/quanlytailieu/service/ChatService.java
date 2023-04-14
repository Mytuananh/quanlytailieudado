package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.entity.ChatEntity;
import com.dado.quanlytailieu.entity.MessageEntity;
import com.dado.quanlytailieu.repository.ChatRepository;
import com.dado.quanlytailieu.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ChatService {
    private ChatRepository chatRepository;

    private MessageRepository messageRepository;

    public Long createAndOrGetChat(String to) {
        var chatEntity = chatRepository.findByName(to);
        if(chatEntity.isPresent()) {
            return chatEntity.get().getChatId();
        } else {
            ChatEntity newChat = new ChatEntity();
            newChat.setName(to);
            return chatRepository.save(newChat).getChatId();
        }
    }

    public List<MessageEntity> getMessages(String chat) {
        var chatEntity = chatRepository.findByName(chat);
        if(chatEntity.isPresent()) {
            return messageRepository.findAllByChatId(chatEntity.get().getChatId());
        } else {
            return new ArrayList<>();
        }
    }
}
