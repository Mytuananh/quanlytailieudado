package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.dao.FileInfoDto;
import com.dado.quanlytailieu.dao.FileUploadDto;
import com.dado.quanlytailieu.model.FileEntity;
import com.dado.quanlytailieu.repository.FileRepository;
import com.dado.quanlytailieu.service.FileService;
import com.dado.quanlytailieu.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin("*")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/all")
    public ResponseEntity<List<FileInfoDto>> getAllFileName() {
        return ResponseEntity.ok(fileService.getAllFileName());
    }

    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadFile(@ModelAttribute FileUploadDto fileUploadDTO, @RequestHeader String authorization) throws IOException {
        String jwt;
        jwt = authorization.substring(7);
        String createdUser = jwtService.extractUsername(jwt);
        return ResponseEntity.ok(fileService.uploadFile(fileUploadDTO, createdUser));
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws FileNotFoundException {
        return fileService.downloadFile(fileId);
    }

}
