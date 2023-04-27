package com.dado.quanlytailieu.controller;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;

import com.dado.quanlytailieu.command.ConstructionDocumentCommand;
import com.dado.quanlytailieu.dao.CreateHoSoCT;
import com.dado.quanlytailieu.dto.*;
import com.dado.quanlytailieu.entity.HoSoCongTrinh;
import com.dado.quanlytailieu.enums.CongTrinhType;
import com.dado.quanlytailieu.repository.HoSoCongTrinhRepository;
import com.dado.quanlytailieu.service.HoSoCongTrinhService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ho-so-cong-trinh")
@CrossOrigin("*")
@AllArgsConstructor
public class HoSoCongTrinhController {

    private HoSoCongTrinhService hoSoCongTrinhService;
    private HoSoCongTrinhRepository hoSoCongTrinhRepository;

    @GetMapping("/count")
    public HoSoCTCountDTO getAllConstruction() {
        return hoSoCongTrinhService.getAllCongTrinhCount();
    }

    @GetMapping(path = "/type")
    public List<HoSoCTDTO> getConstructionByType(@RequestParam CongTrinhType type) {
        return hoSoCongTrinhService.getHoSoCongTrinhByType(type);
    }

    @PostMapping("/create")
    public HoSoCTDTO createHoSoCT(CreateHoSoCT hsct) {
        return hoSoCongTrinhService.createHoSoCT(hsct);
    }
}
