package com.dado.quanlytailieu.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class FileInfoDto {
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
}