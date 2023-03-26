package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.dao.FileInfoDto;
import com.dado.quanlytailieu.dao.FileUploadDto;
import com.dado.quanlytailieu.entity.FileEntity;
import com.dado.quanlytailieu.repository.FileRepository;
import com.dado.quanlytailieu.service.FileService;
import com.dado.quanlytailieu.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin("*")
public class FileController {

    @Value("${extern.resources.path.file}")
    private String path;

    @Autowired
    private FileService fileService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws FileNotFoundException {
        return fileService.downloadFile(fileId);
    }

    @GetMapping("/info/{fileId}")
    public ResponseEntity<FileInfoDto> getFileInfo(@PathVariable Long fileId) throws FileNotFoundException {
        return ResponseEntity.ok(fileService.getFileInfo(fileId));
    }

    @GetMapping(value = "/preview/pdf/{fileId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> getPreviewFile(@PathVariable Long fileId) {
        return fileService.getPreviewFile(fileId);
    }

}
