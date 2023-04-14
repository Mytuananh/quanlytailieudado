package com.dado.quanlytailieu.dto;

import com.dado.quanlytailieu.entity.UserEntity;

public record UserInfoDTO(long id, String username, String firstName, String propic) {

    public static UserInfoDTO toUserInfoDTO(UserEntity user) {
        return new UserInfoDTO(user.getId(), user.getUsername(), user.getUsername(), null);
    }
}
