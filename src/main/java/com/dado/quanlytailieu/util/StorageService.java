package com.dado.quanlytailieu.util;

import com.dado.quanlytailieu.config.WebProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final WebProperties webProperties;

    public String saveImageItem(int itemId, MultipartFile file) {
        var filename = itemId + "_" + System.currentTimeMillis() + getDotExtension(file);
        saveFile(webProperties.imageItemLocation() + filename, file);
        return "/image/item/" + filename;
    }

    private static void saveFile(String location, MultipartFile file) {
        try {
            file.transferTo(new File(location));
        } catch (Exception e) {
            throw new AppException("Upload file error : " + e.getMessage());
        }
    }

    // return empty string or ".{extension}"
    // example "" or ".jpg"
    private static String getDotExtension(MultipartFile file) {
        return Optional.ofNullable(file.getOriginalFilename())
                .filter(f -> f.contains("."))
                .map(f -> "." + f.substring(f.lastIndexOf(".") + 1))
                .orElse("");
    }
}
