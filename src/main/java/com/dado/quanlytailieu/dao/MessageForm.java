package com.dado.quanlytailieu.dao;

import com.dado.quanlytailieu.entity.RoomChatEntity;
import com.dado.quanlytailieu.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageForm {
    private String content;
    private Long userId;
    private Long roomChatId;
}
