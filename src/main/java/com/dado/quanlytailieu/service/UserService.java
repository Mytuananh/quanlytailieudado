package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.dto.UserInfoDTO;
import com.dado.quanlytailieu.entity.RoomChatEntity;
import com.dado.quanlytailieu.entity.UserEntity;
import com.dado.quanlytailieu.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public UserInfoDTO getUserByUserName(String name) {
        return UserInfoDTO.toUserInfoDTO(userRepository.findByUsername(name));
    }

    public UserInfoDTO getUserInfoDTOByEmail(String email) {
        return UserInfoDTO.toUserInfoDTO(userRepository.findByEmail(email).orElseThrow());
    }

    public List<UserEntity> getAllUserListByListId(List<Long> ids) {
        return userRepository.findAllByIdIsIn(ids);
    }

    public Iterable<RoomChatEntity> findAllRoomChatByPresentUser(String email) {
        return userRepository.findByEmail(email).orElseThrow().getRoomChatEntities();
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
