package com.dado.quanlytailieu.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class FileInfoDto {
    Long id;
    String fileName;
    String code;
    String tenCongTrinh;
    String createdUser;
    LocalDateTime createdTime;
}
