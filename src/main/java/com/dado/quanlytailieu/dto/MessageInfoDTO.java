package com.dado.quanlytailieu.dto;

import com.dado.quanlytailieu.entity.MessageEntity;
import com.dado.quanlytailieu.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MessageInfoDTO {
    private long id;
    private String content;
    private U sender;

    @Data
    @Builder
    static class U {
        long id;
        String name;
        String avatar;
    }
    boolean status;

    static MessageInfoDTO toMessageInfoDTO(MessageEntity message) {
        return MessageInfoDTO.builder()
                .id(message.getId())
                .content(message.getContent())
                .status(message.isStatus())
                .sender(
                    U.builder()
                        .id(message.getSender().getId())
                        .name(message.getSender().getUsername())
                        .avatar(message.getSender().getAvatar())
                        .build()
                )
                .build();
    }

    public static List<MessageInfoDTO> toMessageInfoDTO(List<MessageEntity> messages) {
        return messages.stream().map(MessageInfoDTO::toMessageInfoDTO).toList();
    }

}
