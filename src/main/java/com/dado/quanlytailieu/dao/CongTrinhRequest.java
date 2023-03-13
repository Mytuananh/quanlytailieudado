package com.dado.quanlytailieu.dao;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CongTrinhRequest {
    private String name;
    private String code;
    private String tenCongTrinh;
    private String address;
    private String ownUser;
    private MultipartFile file;
    private String city;
    private String country;
    private String postCode;
    private String description;
    private String createdUser;
}
