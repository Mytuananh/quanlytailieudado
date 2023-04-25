package com.dado.quanlytailieu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "chat")
public class RoomChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        // receiveId

    private String name;

    @OneToMany(mappedBy = "roomChat")
    private List<MessageEntity> messages;

    @ManyToMany(mappedBy = "roomChatEntities")
    private List<UserEntity> userEntityList;
}
