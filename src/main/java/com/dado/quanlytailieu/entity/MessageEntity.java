package com.dado.quanlytailieu.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "messages")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity sender;

    boolean status;

    @JsonBackReference
    @ManyToOne(targetEntity = RoomChatEntity.class)
    private RoomChatEntity roomChat;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
