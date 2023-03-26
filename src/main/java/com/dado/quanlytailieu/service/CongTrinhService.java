package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.command.QuanLyCongTrinhRequest;
import com.dado.quanlytailieu.dto.HoSoCongTrinhDto;
import com.dado.quanlytailieu.dto.QuanLyCongTrinhDTO;
import com.dado.quanlytailieu.entity.FileEntity;
import com.dado.quanlytailieu.entity.HoSoCongTrinh;
import com.dado.quanlytailieu.entity.Image;
import com.dado.quanlytailieu.enums.CongTrinhType;
import com.dado.quanlytailieu.entity.CongTrinh;
import com.dado.quanlytailieu.mapper.CongTrinhMapper;
import com.dado.quanlytailieu.repository.CongTrinhRepository;
import com.dado.quanlytailieu.repository.HoSoCongTrinhRepository;
import com.dado.quanlytailieu.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CongTrinhService {

    @Autowired
    CongTrinhRepository congTrinhRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    HoSoCongTrinhRepository hoSoCongTrinhRepository;


    public CongTrinh createCongTrinh(CongTrinh congTrinhRequest, List<FileEntity> fileEntities, List<Image> images) {
        Integer count = congTrinhRepository.countCongTrinhsByType(congTrinhRequest.getType()) + 1;
        congTrinhRequest.setMaCT(String.format("%s%d", congTrinhRequest.getType().getValue(), count));
        congTrinhRequest.setFiles(fileEntities.stream().map(FileEntity::getId).toList());
        congTrinhRequest.setImages(images.stream().map(Image::getId).toList());
        return congTrinhRepository.save(congTrinhRequest);
    }

    public CongTrinh updateConstruction(Long constructionId, QuanLyCongTrinhRequest command) throws Exception {
        CongTrinh construction = congTrinhRepository.findById(constructionId).get();
        construction.setName(command.getName());
        construction.setMaCT(command.getMaCT());
        construction.setViTri(command.getViTri());
        construction.setQuyMo(command.getQuyMo());
        return congTrinhRepository.save(construction);
    }

    public List<CongTrinh> getConstructionByType(CongTrinhType type) {
        return congTrinhRepository.getCongTrinhByTypeOrderById(type);
    }

    public QuanLyCongTrinhDTO getDataQuanLyCongTrinhByMaCT(String maCT) {
        CongTrinh ct = congTrinhRepository.getCongTrinhByMaCT(maCT);
        List<String> tenCongTrinhLienQuan = congTrinhRepository.getCongTrinhsByIdIsIn(ct.getCongTrinhLienQuan()).stream().map(CongTrinh::getName).toList();
        return CongTrinhMapper.toQuanLyCongTrinhDTO(ct, tenCongTrinhLienQuan);
    }

    public CongTrinh findCongTrinhById(Long id) {
        return congTrinhRepository.findById(id).orElseThrow();
    }

    public CongTrinh updateCongTrinh(CongTrinh congTrinh, List<FileEntity> fileEntities, List<Image> images) {
        if (congTrinh.getMaCT().isBlank()) {
            Integer count = congTrinhRepository.countCongTrinhsByType(congTrinh.getType()) + 1;
            congTrinh.setMaCT(String.format("%s%d", congTrinh.getType().getValue(), count));
        }
        fileEntities.forEach(fileEntity -> congTrinh.getFiles().add(fileEntity.getId()));
        images.forEach(image -> congTrinh.getImages().add(image.getId()));
        return congTrinhRepository.save(congTrinh);
    }
}
