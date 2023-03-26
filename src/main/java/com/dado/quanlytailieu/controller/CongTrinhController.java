package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.command.QuanLyCongTrinhRequest;
import com.dado.quanlytailieu.dto.QuanLyCongTrinhDTO;
import com.dado.quanlytailieu.entity.FileEntity;
import com.dado.quanlytailieu.entity.Image;
import com.dado.quanlytailieu.enums.CongTrinhType;
import com.dado.quanlytailieu.entity.CongTrinh;
import com.dado.quanlytailieu.service.CongTrinhService;
import com.dado.quanlytailieu.service.FileService;
import com.dado.quanlytailieu.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/cong-trinh")
@CrossOrigin("*")
public class CongTrinhController {

    @Value("${extern.resources.path.file}")
    private String pathFile;

    @Value("${extern.resources.path.image}")
    private String pathImage;

    @Autowired
    CongTrinhService congTrinhService;

    @Autowired
    ImageService imageService;

    @Autowired
    FileService fileService;

//    @GetMapping("/all")
//    public List<ConstructionDto> getAllConstruction() {
//        return constructionService.getAllConstruction();
//    }

    @PostMapping("/update/{constructionId}")
    public ResponseEntity<?> updateConstruction(@PathVariable Long constructionId, @RequestBody QuanLyCongTrinhRequest command
    ) throws Exception {
        return ResponseEntity.ok().body(congTrinhService.updateConstruction(constructionId, command));
    }

    @GetMapping("/type")
    public List<CongTrinh> getConstructionByType(@RequestParam CongTrinhType type) {
        return congTrinhService.getConstructionByType(type);
    }

    @GetMapping("/maCongTrinh")
    public QuanLyCongTrinhDTO getDataQuanLyCongTrinhByMaCT(@RequestParam String maCT) {
        return congTrinhService.getDataQuanLyCongTrinhByMaCT(maCT);
    }

    @PostMapping(value = "/create-cong-trinh")
    public ResponseEntity<Object> createCongTrinh(
//            @RequestParam("maCT") String maCT,
            @RequestParam("name") String name,
            @RequestParam("viTri") String viTri,
            @RequestParam("type") CongTrinhType type,
            @RequestParam("quyMo") String quyMo,
            @RequestParam("thietBi") String thietBi,
            @RequestParam("congTrinhLienQuan") List<Long> congTrinhLienQuan,
            @RequestParam("thongTinKhac") String thongTinKhac,
            MultipartHttpServletRequest request) throws Exception {

        CongTrinh congTrinhRequest = new CongTrinh();
//        congTrinhRequest.setMaCT(maCT);
        congTrinhRequest.setName(name);
        congTrinhRequest.setViTri(viTri);
        congTrinhRequest.setType(type);
        congTrinhRequest.setQuyMo(quyMo);
        congTrinhRequest.setThietBi(thietBi);
        congTrinhRequest.setCongTrinhLienQuan(congTrinhLienQuan);
        congTrinhRequest.setThongTinKhac(thongTinKhac);

        List<FileEntity> fileEntities = fileService.saveFileForCongtrinh(request.getFiles("files"));
        List<Image> images = imageService.storeImageForConstruction(request.getFiles("images"));
        CongTrinh congTrinh = congTrinhService.createCongTrinh(congTrinhRequest, fileEntities, images);

        return ResponseEntity.ok(congTrinh);
    }

    @PostMapping(value = "/update-cong-trinh")
    public ResponseEntity<Object> updateCongTrinh(
//            @RequestParam("maCT") String maCT,
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("viTri") String viTri,
            @RequestParam("type") CongTrinhType type,
            @RequestParam("quyMo") String quyMo,
            @RequestParam("thietBi") String thietBi,
            @RequestParam("restCongTrinhLienQuan") List<Long> restCongTrinhLienQuan,
            @RequestParam("thongTinKhac") String thongTinKhac,
            @RequestParam("restImage") List<Long> restImage,
            @RequestParam("restFile") List<Long> restFile,
            MultipartHttpServletRequest request) throws Exception {

        CongTrinh congtrinh = congTrinhService.findCongTrinhById(id);
//        congTrinhRequest.setMaCT(maCT);
        congtrinh.setName(name);
        congtrinh.setViTri(viTri);
        congtrinh.setType(type);
        congtrinh.setQuyMo(quyMo);
        congtrinh.setThietBi(thietBi);
        congtrinh.setCongTrinhLienQuan(restCongTrinhLienQuan);
        congtrinh.setThongTinKhac(thongTinKhac);
        congtrinh.setImages(restImage);
        congtrinh.setFiles(restFile);

        List<FileEntity> fileEntities = fileService.saveFileForCongtrinh(request.getFiles("files"));
        List<Image> images = imageService.storeImageForConstruction(request.getFiles("images"));
        CongTrinh ct = congTrinhService.updateCongTrinh(congtrinh, fileEntities, images);
        return ResponseEntity.ok("success");
    }

}
