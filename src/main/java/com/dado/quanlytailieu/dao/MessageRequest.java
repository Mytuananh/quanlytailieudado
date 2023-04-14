package com.dado.quanlytailieu.dao;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageRequest {
    long senderId;
    long receiverId;
    String content;
    LocalDateTime createdAt;
}
