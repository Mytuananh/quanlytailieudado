package com.dado.quanlytailieu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String url;
    private Boolean status;
    @JsonIgnore
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;

}
