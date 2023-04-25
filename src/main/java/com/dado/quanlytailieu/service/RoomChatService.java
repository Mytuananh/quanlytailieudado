package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.entity.RoomChatEntity;
import com.dado.quanlytailieu.entity.UserEntity;
import com.dado.quanlytailieu.repository.RoomChatRepository;
import com.dado.quanlytailieu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomChatService {
    @Autowired
    private RoomChatRepository roomChatRepository;

    @Autowired
    private UserRepository userRepository;

    public Iterable<RoomChatEntity> findAll() {
        return roomChatRepository.findAll();
    }

    public Optional<RoomChatEntity> findById(Long id) {
        return roomChatRepository.findById(id);
    }

    public RoomChatEntity save(RoomChatEntity roomChat) {
        return roomChatRepository.save(roomChat);
    }

    public void deleteById(Long id) {
        roomChatRepository.deleteById(id);
    }

    public Iterable<RoomChatEntity> findAllByUser(UserEntity user) {
        return userRepository.findById(user.getId()).orElseThrow().getRoomChatEntities();
    }

    public Optional<RoomChatEntity> findByRoomChatId(Long roomChatId) {
        return roomChatRepository.findById(roomChatId);
    }

    public Iterable<RoomChatEntity> findAllByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow().getRoomChatEntities();
    }
}
