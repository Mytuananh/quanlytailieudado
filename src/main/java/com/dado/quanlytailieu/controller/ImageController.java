package com.dado.quanlytailieu.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import com.dado.quanlytailieu.command.ImageUpdateCommand;
import com.dado.quanlytailieu.dto.ResponseDto;
import com.dado.quanlytailieu.dto.UploadImageResponse;
import com.dado.quanlytailieu.repository.CongTrinhRepository;
import com.dado.quanlytailieu.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Value("${extern.resources.path.image}")
    private String path;

    @Autowired
    ImageService imageService;

    @Autowired
    CongTrinhRepository congTrinhRepository;



    @PostMapping("/upload")
    public ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            // Lưu file vào server
            String fileName = file.getOriginalFilename();
            File targetFile = new File(path + "/" + fileName);
            file.transferTo(targetFile);

            // Tạo đường dẫn URL cho file đã lưu
            String fileUrl = "http://localhost:8080/uploads/" + fileName;

            // Trả về URL của file đã lưu
            return ResponseEntity.ok().body(new UploadImageResponse(fileUrl, fileName));
        } catch (IOException e) {
            // Xử lý nếu có lỗi khi lưu file
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/preview/{imageId}")
    public ResponseEntity<Resource> getPreviewFile(@PathVariable Long imageId) throws MalformedURLException {
        return imageService.getPreviewFile(imageId);

    }

    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = loadAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            return null;
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    public Resource loadAsResource(String fileName) {
        try {
            Path file = Paths.get(path).resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw   new RuntimeException("Could not read file: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + fileName, e);
        }
    }
}
