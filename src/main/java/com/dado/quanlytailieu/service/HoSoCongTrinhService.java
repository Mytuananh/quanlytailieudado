package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.dao.CreateHoSoCT;
import com.dado.quanlytailieu.dto.*;
import com.dado.quanlytailieu.entity.HoSoCongTrinh;
import com.dado.quanlytailieu.enums.CongTrinhType;
import com.dado.quanlytailieu.repository.CongTrinhRepository;
import com.dado.quanlytailieu.repository.FileRepository;
import com.dado.quanlytailieu.repository.HoSoCongTrinhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

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

    @Value("${extern.resources.path.file}")
    private String pathFile;

    public HoSoCTCountDTO getAllCongTrinhCount() {

        HoSoCTCountDTO dto = new HoSoCTCountDTO();
        dto.setCongDuoiDe(hoSoCongTrinhRepository.countHoSoCongTrinhByType(CongTrinhType.CONG));
        dto.setTramBom(hoSoCongTrinhRepository.countHoSoCongTrinhByType(CongTrinhType.TRAM_BOM));
        dto.setCongTrinhTrenKenh(hoSoCongTrinhRepository.countHoSoCongTrinhByType(CongTrinhType.KENH_XAY));
        dto.setKenhCapI(hoSoCongTrinhRepository.countHoSoCongTrinhByType(CongTrinhType.KENH_DAT));
        dto.setKenhTuoiSauTB(hoSoCongTrinhRepository.countHoSoCongTrinhByType(CongTrinhType.KENH_TUOI));
        return dto;
    }

    public List<HoSoCTDTO> getHoSoCongTrinhByType(CongTrinhType type) {
        var hsctList = hoSoCongTrinhRepository.getHoSoCongTrinhByType(type);
        return hsctList.stream().map(this::toHoSoCTDTO).toList();
    }

    HoSoCTDTO toHoSoCTDTO(HoSoCongTrinh hsct) {
        return HoSoCTDTO.builder()
                .ctName(hsct.getCtName())
                .maHS(hsct.getMaHS())
                .nameHS(hsct.getNameHS())
                .loaiHS(hsct.getLoaiHS())
                .namTaoHS(hsct.getNamTaoHS())
                .namHoanThanhHS(hsct.getNamHoanThanhHS())
                .donViLapHS(hsct.getDonViLapHS())
                .build();
    }

    public HoSoCTDTO createHoSoCT(CreateHoSoCT hsct) {
        var hsctEntity = HoSoCongTrinh.builder()
                .build();
        return toHoSoCTDTO(hoSoCongTrinhRepository.save(hsctEntity));
    }

}
