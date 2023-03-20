package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.command.ConstructionCommand;
import com.dado.quanlytailieu.enums.CongTrinhType;
import com.dado.quanlytailieu.entity.CongTrinh;
import com.dado.quanlytailieu.service.CongTrinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/construction")
@CrossOrigin("*")
public class CongTrinhController {

    @Autowired
    CongTrinhService congTrinhService;

//    @GetMapping("/all")
//    public List<ConstructionDto> getAllConstruction() {
//        return constructionService.getAllConstruction();
//    }

    @PostMapping("/create")
    public ResponseEntity<?> createConstruction(@RequestBody ConstructionCommand command
    ) throws Exception {
        return ResponseEntity.ok().body(congTrinhService.createConstruction(command));
    }

    @PostMapping("/update/{constructionId}")
    public ResponseEntity<?> updateConstruction(@PathVariable Long constructionId, @RequestBody ConstructionCommand command
    ) throws Exception {
        return ResponseEntity.ok().body(congTrinhService.updateConstruction(constructionId, command));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllConstruction() throws MalformedURLException {
        return ResponseEntity.ok().body(congTrinhService.getAllContruction());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConstructionById(@PathVariable(name = "id") String id) throws MalformedURLException {
        var cons = congTrinhService.findConstructionById(id);
        if(Objects.isNull(cons)){
            return ResponseEntity.badRequest().body("Construction is not exist!");
        }
        return ResponseEntity.ok().body(cons);
    }

    @GetMapping("/type")
    public List<CongTrinh> getConstructionByType(@RequestParam CongTrinhType type) {
        return congTrinhService.getConstructionByType(type);
    }
}
