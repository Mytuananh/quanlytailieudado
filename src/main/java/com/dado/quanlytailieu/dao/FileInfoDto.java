package com.dado.quanlytailieu.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FileInfoDto {
    Long id;
    String fileName;
    String createdUser;
    LocalDateTime createdTime;
}
