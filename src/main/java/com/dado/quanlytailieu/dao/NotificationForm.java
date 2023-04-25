package com.dado.quanlytailieu.dao;

import com.dado.quanlytailieu.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationForm {
    private String content;
    private String url;
    private UserEntity user;
}
