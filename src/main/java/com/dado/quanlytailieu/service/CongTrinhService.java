package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.command.ConstructionCommand;
import com.dado.quanlytailieu.dto.CongTrinhDto;
import com.dado.quanlytailieu.enums.CongTrinhType;
import com.dado.quanlytailieu.entity.CongTrinh;
import com.dado.quanlytailieu.repository.CongTrinhRepository;
import com.dado.quanlytailieu.repository.HoSoCongTrinhRepository;
import com.dado.quanlytailieu.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    HoSoCongTrinhService hoSoCongTrinhService;

    public CongTrinh createConstruction(ConstructionCommand command) throws Exception {
        CongTrinh congTrinh = new CongTrinh();
        congTrinh.setName(command.getName());
        congTrinh.setCode(command.getCode());
        congTrinh.setLocation(command.getLocation());
        congTrinh.setSize(command.getArea());
        congTrinh.setType(CongTrinhType.valueOf(command.getType()));
        var cons = congTrinhRepository.save(congTrinh);
        return cons;
    }

    public CongTrinh updateConstruction(Long constructionId, ConstructionCommand command) throws Exception {
        var construction = congTrinhRepository.findById(constructionId).get();
        construction.setName(command.getName());
        construction.setCode(command.getCode());
        construction.setLocation(command.getLocation());
        construction.setSize(command.getArea());
        construction.setType(CongTrinhType.valueOf(command.getType()));
        return congTrinhRepository.save(construction);
    }


    public CongTrinhDto findConstructionById(String id) throws MalformedURLException {
        var construction = congTrinhRepository.findById(Long.valueOf(id)).orElse(null);
        var docs = hoSoCongTrinhService.getConstructionDocsByConstructionId(id);
        if(Objects.isNull(construction)){
            return null;
        }
        CongTrinhDto dto = new CongTrinhDto();
        dto.setName(construction.getName());
        dto.setCode(construction.getCode());
        dto.setLocation(construction.getLocation());
        dto.setType(construction.getType());
        dto.setDocs(docs);
        return dto;
    }

    public List<CongTrinhDto> getAllContruction() throws MalformedURLException {
        var constructions = congTrinhRepository.findAll();
        List<CongTrinhDto> congTrinhDtos = new ArrayList<>();
        for (CongTrinh congTrinh :constructions) {
            var docs = hoSoCongTrinhService
                    .getConstructionDocsByConstructionId(String.valueOf(congTrinh.getId()));
            CongTrinhDto congTrinhDto = new CongTrinhDto();
            congTrinhDto.setLocation(congTrinh.getLocation());
            congTrinhDto.setCode(congTrinh.getCode());
            congTrinhDto.setName(congTrinh.getName());
            congTrinhDto.setType(congTrinh.getType());
            congTrinhDto.setSize(congTrinh.getSize());
            congTrinhDto.setDocs(docs);
            congTrinhDtos.add(congTrinhDto);
        }
        return congTrinhDtos;
    }

    public List<CongTrinh> getConstructionByType(CongTrinhType type) {
        return congTrinhRepository.getCongTrinhByTypeOrderById(type);
    }
}
