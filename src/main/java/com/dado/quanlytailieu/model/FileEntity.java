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

    private String url;

    private String code;

    private String tenCongTrinh;

    @OneToMany(mappedBy = "fileEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> imageList;

    private String createdUser;

    @CreatedDate
    private LocalDateTime createdTime;
}
