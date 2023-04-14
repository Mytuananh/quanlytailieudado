package com.dado.quanlytailieu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "chat")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;        // receiveId

    private String name;
//    @Column(name = "sender_id")
//    private Long senderId;

    @OneToMany(mappedBy = "id")
    private List<UserEntity> member;
}
