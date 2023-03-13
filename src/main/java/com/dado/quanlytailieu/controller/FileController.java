package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.dao.FileUploadDto;
import com.dado.quanlytailieu.dto.ResponseDto;
import com.dado.quanlytailieu.model.FileEntity;
import com.dado.quanlytailieu.service.FileService;
import com.dado.quanlytailieu.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/files")
public class FileController {

    @Value("${extern.resoures.path}")
    private String path;

    @Autowired
    private FileService fileService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/all")
    public ResponseDto getAllFileName() {
        var files = fileService.getAllFileName();
        return ResponseDto.builder()
                .message("Successfully!")
                .httpCode(HttpStatus.OK)
                .body(files.getBody()).build();
    }

    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadFile(@ModelAttribute FileUploadDto fileUploadDTO, @RequestHeader String authorization) throws IOException {
        String jwt;
        jwt = authorization.substring(7);
        String createdUser = jwtService.extractUsername(jwt);
        return ResponseEntity.ok(fileService.uploadFile(fileUploadDTO, createdUser));
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> downloadFile(@PathVariable Long fileId) throws FileNotFoundException {
        return fileService.downloadFile(fileId);
    }

    @GetMapping(value = "/file/{fileName:.+}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> getPreviewFile(@PathVariable String fileName) {
        Path filePath = Paths.get(path, fileName);

        try {
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", "inline; filename=" + fileName)
                        .contentLength(Files.size(filePath))
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }

}
