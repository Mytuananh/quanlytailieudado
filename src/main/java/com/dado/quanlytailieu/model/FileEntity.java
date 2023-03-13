package com.dado.quanlytailieu.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String fileName;
    private String code;
    private String tenCongTrinh;
    private String address;
    private String ownUser;
    private String city;
    private String country;
    private String postCode;
    private String description;

    @OneToMany(mappedBy = "fileEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> imageList;

    private String createdUser;

    @CreatedDate
    private LocalDateTime createdTime;
}
