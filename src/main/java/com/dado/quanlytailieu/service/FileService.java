package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.dao.FileInfoDto;
import com.dado.quanlytailieu.entity.CongTrinh;
import com.dado.quanlytailieu.entity.FileEntity;
import com.dado.quanlytailieu.repository.FileRepository;
import com.dado.quanlytailieu.util.DateTimeConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Value("${extern.resources.path.file}")
    private String filePath;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ResourceLoader resourceLoader;
//
//    public FileEntity uploadFile(FileUploadDto fileUploadDTO, String createdUser) throws IOException {
//        MultipartFile file = fileUploadDTO.getFile();
//        String filename = saveFile(file);
//        FileEntity fileEntity = new FileEntity();
//        fileEntity.setFileName(filename);
//        fileEntity.setCreatedUser(createdUser);
//        fileEntity.setType(file.getContentType());
//        fileEntity = fileRepository.save(fileEntity);
//        return fileEntity;
//    }

    public ResponseEntity<Resource> downloadFile(Long fileId) throws FileNotFoundException {

        FileEntity file = fileRepository.findById(fileId).orElseThrow(FileNotFoundException::new);

        String fullPath = filePath + "/" + file.getFileName();

        Resource resource = null;
        resource = resourceLoader.getResource("file:" + fullPath);
        if (!resource.exists()) {
            throw new FileNotFoundException();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public FileInfoDto getFileInfo(Long fileId) throws FileNotFoundException {
        return convertFileEntityToFileInfoDto(fileRepository.findById(fileId).orElseThrow(FileNotFoundException::new));
    }

    private String decideFullPath(MultipartFile file) {
        String filename = file.getOriginalFilename();
        int index = filename.indexOf('.');
        String extension = filename.substring(index+1).toUpperCase();
        return filePath + File.separator + File.separator+ filename;
    }

    private FileInfoDto convertFileEntityToFileInfoDto(FileEntity file) {
        return FileInfoDto.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .build();
    }

    public String saveFile(MultipartFile file) {
        if(file.isEmpty())
        {
            throw  new RuntimeException("please provide a valide file");
        }

        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(file.getInputStream());
            byte[] b = in.readAllBytes();
            String fullPath = decideFullPath(file);
            out = new BufferedOutputStream(new FileOutputStream(fullPath));
            out.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return StringUtils.cleanPath(file.getOriginalFilename());
    }

    public List<FileEntity> saveFileForCongtrinh(List<MultipartFile> files) throws IOException {
        List<FileEntity> fileEntities = new ArrayList<>();
        File fileDir = new File(filePath);
        if (!fileDir.exists()) { // Check if the directory exists and create it if it doesn't
            fileDir.mkdirs();
        }
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String fullPath = filePath + fileName;
            File convFile = new File(fullPath);
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(fileName);
            fileEntity.setFilePath(fullPath);
            fileEntity.setSize(file.getSize());
            fileEntities.add(fileEntity);
        }
        return fileRepository.saveAll(fileEntities);
    }

    public ResponseEntity<Resource> getPreviewFile(String fileName) {
//        FileEntity fileEntity = fileRepository.findByFileName(fileName);
        Path path = Paths.get(filePath, fileName);

        try {
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {
                String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + encodedFileName)
                        .contentLength(Files.size(path))
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }
}
