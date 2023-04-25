package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.dto.MessageInfoDTO;
import com.dado.quanlytailieu.entity.MessageEntity;
import com.dado.quanlytailieu.entity.RoomChatEntity;
import com.dado.quanlytailieu.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService{
    @Autowired
    private MessageRepository messageRepository;
    public Iterable<MessageEntity> findAll() {
        return messageRepository.findAll();
    }

    public MessageEntity save(MessageEntity message) {
        return messageRepository.save(message);
    }

    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }

    public List<MessageInfoDTO> findAllByRoomChat(RoomChatEntity roomChat) {
        return MessageInfoDTO.toMessageInfoDTO(messageRepository.findAllByRoomChat(roomChat));
    }
}
