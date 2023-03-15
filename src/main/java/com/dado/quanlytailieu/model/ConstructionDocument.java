package com.dado.quanlytailieu.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
public class ConstructionDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String ownUser;

    String createdUser;

    @CreatedDate
    LocalDateTime createdTime;

    @UpdateTimestamp
    LocalDateTime updatedTime;

    @ManyToOne
    @JoinColumn(name = "construction_id")
    Construction construction;

    @OneToOne
    @JoinColumn(name = "file_id")
    FileEntity file;
}