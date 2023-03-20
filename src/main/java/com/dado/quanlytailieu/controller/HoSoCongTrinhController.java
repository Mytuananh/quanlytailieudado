package com.dado.quanlytailieu.controller;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;

import com.dado.quanlytailieu.command.ConstructionDocumentCommand;
import com.dado.quanlytailieu.dto.HoSoCongTrinhDto;
import com.dado.quanlytailieu.entity.HoSoCongTrinh;
import com.dado.quanlytailieu.service.HoSoCongTrinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
public class HoSoCongTrinhController {
    @Autowired
    HoSoCongTrinhService hoSoCongTrinhService;

    @PostMapping("/create")
    public ResponseEntity<HoSoCongTrinh> createDocument(@ModelAttribute ConstructionDocumentCommand command) throws Exception {
        return ResponseEntity.ok()
                .body(hoSoCongTrinhService.createConstructionDocs(command));
    }

    @GetMapping("{id}")
    public ResponseEntity<HoSoCongTrinhDto> getDocsById(@RequestParam(name = "id") String id) throws MalformedURLException {
        var docs = hoSoCongTrinhService.getConstructionDocsById(id);
        if(Objects.isNull(docs)){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok()
                .body(docs);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<HoSoCongTrinhDto>>getAll() throws MalformedURLException {
        return ResponseEntity.ok()
                .body(hoSoCongTrinhService.getAllDocs());
    }
}
