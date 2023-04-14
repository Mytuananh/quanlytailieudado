package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.dto.UserInfoDTO;
import com.dado.quanlytailieu.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public UserInfoDTO getUserByUserName(String name) {
        return UserInfoDTO.toUserInfoDTO(userRepository.findByUsername(name));
    }
}
