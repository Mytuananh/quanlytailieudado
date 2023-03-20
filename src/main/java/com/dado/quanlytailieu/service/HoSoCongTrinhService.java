package com.dado.quanlytailieu.service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.dado.quanlytailieu.command.ConstructionDocumentCommand;
import com.dado.quanlytailieu.dto.HoSoCongTrinhDto;
import com.dado.quanlytailieu.entity.HoSoCongTrinh;
import com.dado.quanlytailieu.entity.FileEntity;
import com.dado.quanlytailieu.repository.CongTrinhRepository;
import com.dado.quanlytailieu.repository.HoSoCongTrinhRepository;
import com.dado.quanlytailieu.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HoSoCongTrinhService {
    @Autowired
    HoSoCongTrinhRepository hoSoCongTrinhRepository;

    @Autowired
    CongTrinhRepository congTrinhRepository;

    @Autowired
    FileService fileService;

    @Autowired
    FileRepository fileRepository;

    @Value("${extern.resources.path}")
    private String path;

    public HoSoCongTrinh createConstructionDocs(ConstructionDocumentCommand command) throws Exception {

        MultipartFile file = command.getFile();
        String filename = fileService.saveFile(file);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(filename);
        fileEntity.setCreatedUser(command.getCreatedUser());
        fileEntity.setType(file.getContentType());
        fileRepository.save(fileEntity);

        var construction = congTrinhRepository.findById(command.getConstructionId()).orElseThrow(()->new Exception("construction not exist"));
        HoSoCongTrinh document = new HoSoCongTrinh();
        document.setName(command.getName());
        document.setOwnUser(command.getOwnUser());
        document.setCreatedUser(command.getCreatedUser());
        document.setCongTrinh(construction);
        document.setFile(fileEntity);
        hoSoCongTrinhRepository.save(document);
        return document;
    }

    public HoSoCongTrinhDto getConstructionDocsById(String id) throws MalformedURLException {
        var docs = hoSoCongTrinhRepository.findById(Long.valueOf(id)).orElse(null);
        Path filePath = Paths.get(path, docs.getName());
        Resource resource = new UrlResource(filePath.toUri());
        HoSoCongTrinhDto dto = new HoSoCongTrinhDto();
        dto.setId(docs.getId());
        dto.setOwnUser(docs.getOwnUser());
        dto.setCreatedTime(docs.getCreatedTime());
        dto.setCreatedUser(docs.getCreatedUser());
        dto.setName(docs.getName());
        dto.setFile(resource);

        return dto;
    }

    public List<HoSoCongTrinhDto> getConstructionDocsByConstructionId(String id) throws MalformedURLException {
        var docs = hoSoCongTrinhRepository.getConstructionDocsByConstructionId(id);
        List<HoSoCongTrinhDto> dtos = new ArrayList<>();
        for (HoSoCongTrinh document: docs) {
            Path filePath = Paths.get(path, document.getName());
            Resource resource = new UrlResource(filePath.toUri());
            HoSoCongTrinhDto dto = new HoSoCongTrinhDto();
            dto.setId(document.getId());
            dto.setOwnUser(document.getOwnUser());
            dto.setCreatedTime(document.getCreatedTime());
            dto.setCreatedUser(document.getCreatedUser());
            dto.setName(document.getName());
            dto.setFile(resource);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<HoSoCongTrinhDto> getAllDocs() throws MalformedURLException {
        var list = hoSoCongTrinhRepository.findAll();
        List<HoSoCongTrinhDto> documentDtos = new ArrayList<>();
        for (HoSoCongTrinh document:list) {
            Path filePath = Paths.get(path, document.getName());
            Resource resource = new UrlResource(filePath.toUri());
            HoSoCongTrinhDto dto = new HoSoCongTrinhDto();
            dto.setId(document.getId());
            dto.setOwnUser(document.getOwnUser());
            dto.setCreatedTime(document.getCreatedTime());
            dto.setCreatedUser(document.getCreatedUser());
            dto.setName(document.getName());
            dto.setFile(resource);
            documentDtos.add(dto);
        }
        return documentDtos;
    }
}
