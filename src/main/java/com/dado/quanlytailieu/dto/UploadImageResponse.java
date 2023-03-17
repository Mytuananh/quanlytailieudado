package com.dado.quanlytailieu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadImageResponse {
    private String url;
    private String name;
}
