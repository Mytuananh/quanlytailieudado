package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.command.QuanLyCongTrinhRequest;
import com.dado.quanlytailieu.dto.CongTrinhCountDto;
import com.dado.quanlytailieu.dto.CongTrinhDTO;
import com.dado.quanlytailieu.dto.QuanLyCongTrinhDTO;
import com.dado.quanlytailieu.entity.CongTrinh;
import com.dado.quanlytailieu.entity.FileEntity;
import com.dado.quanlytailieu.entity.Image;
import com.dado.quanlytailieu.entity.LichSuChinhSuaCongTrinh;
import com.dado.quanlytailieu.enums.CongTrinhType;
import com.dado.quanlytailieu.entity.CongTrinh;
import com.dado.quanlytailieu.repository.LichSuChinhSuaCongTrinhRepository;
import com.dado.quanlytailieu.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    LichSuChinhSuaCongTrinhRepository lichSuChinhSuaCongTrinhRepository;

    @Autowired
    LichSuChinhSuaCongTrinhService lichSuChinhSuaCongTrinhService;

    @Autowired
    ExcelService excelService;

    @GetMapping("/count")
    public CongTrinhCountDto getAllConstruction() {
        return congTrinhService.getAllCongTrinhCount();
    }

    @PostMapping("/update/{constructionId}")
    public ResponseEntity<?> updateConstruction(@PathVariable Long constructionId, @RequestBody QuanLyCongTrinhRequest command
    ) throws Exception {
        return ResponseEntity.ok().body(congTrinhService.updateConstruction(constructionId, command));
    }

    @GetMapping(path = "/type")
    public List<CongTrinhDTO> getConstructionByType(@RequestParam CongTrinhType type) {
        return congTrinhService.getConstructionByType(type);
    }

    @GetMapping("/list/images/{maCT}")
    public List<Long> getListImageByMaCT(@PathVariable String maCT) {
        return congTrinhService.getListImageByMaCT(maCT);
    }

    @GetMapping("/list/files/{maCT}")
    public List<String> getListFileByMaCT(@PathVariable String maCT) {
        return congTrinhService.getListFileByMaCT(maCT);
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
            @RequestParam("type") String type,
            @RequestParam("quyMo") String quyMo,
            @RequestParam("thietBi") String thietBi,
            @RequestParam("congTrinhLienQuan") List<String> congTrinhLienQuan,
            @RequestParam("thongTinKhac") String thongTinKhac,
            @RequestParam("soThuTu") String soThuTu,
            MultipartHttpServletRequest request) throws Exception {

        CongTrinh congTrinhRequest = new CongTrinh();
//        congTrinhRequest.setMaCT(maCT);
        congTrinhRequest.setName(name);
        congTrinhRequest.setViTri(viTri);
        congTrinhRequest.setType(CongTrinhType.getType(type));
        congTrinhRequest.setQuyMo(quyMo);
        congTrinhRequest.setThietBi(thietBi);
        congTrinhRequest.setCongTrinhLienQuan(congTrinhLienQuan);
        congTrinhRequest.setThongTinKhac(thongTinKhac);
        congTrinhRequest.setMaCT(type + soThuTu);

        List<FileEntity> fileEntities = fileService.saveFileForCongtrinh(request.getFiles("files"));
        List<Image> images = imageService.storeImageForConstruction(request.getFiles("images"));
        CongTrinh congTrinh = congTrinhService.createCongTrinh(congTrinhRequest, fileEntities, images);

        return ResponseEntity.ok(congTrinh);
    }

    @PostMapping(value = "/update-cong-trinh")
    public ResponseEntity<Object> updateCongTrinh(
//            @RequestParam("maCT") String maCT,
            @RequestParam("maCT") String maCT,
            @RequestParam("name") String name,
            @RequestParam("viTri") String viTri,
            @RequestParam("quyMo") String quyMo,
            @RequestParam("thietBi") String thietBi,
            @RequestParam("congTrinhLienQuan") List<String> congTrinhLienQuan,
            @RequestParam("thongTinKhac") String thongTinKhac,
            MultipartHttpServletRequest request) throws Exception {

        CongTrinh congtrinh = congTrinhService.findCongTrinhByMaCT(maCT);
        LichSuChinhSuaCongTrinh lichSu = new LichSuChinhSuaCongTrinh();
//        congTrinhRequest.setMaCT(maCT);
        if (congtrinh.getName() != name) {
            lichSu.setName(congtrinh.getName() + " -> " + name);
            congtrinh.setName(name);
        }
        if (congtrinh.getViTri() != viTri) {
            lichSu.setViTri(congtrinh.getViTri() + " -> " + viTri);
            congtrinh.setViTri(viTri);
        }
        if (congtrinh.getQuyMo() != quyMo) {
            lichSu.setQuyMo(congtrinh.getQuyMo() + " -> " + quyMo);
            congtrinh.setQuyMo(quyMo);
        }
        if (congtrinh.getThietBi() != thietBi) {
            lichSu.setThietBi(congtrinh.getThietBi() + " -> " + thietBi);
            congtrinh.setThietBi(name);
        }
        if (!congTrinhLienQuan.isEmpty()) {
            lichSu.setCongTrinhLienQuan(congtrinh.getCongTrinhLienQuan().size() + " -> " + congTrinhLienQuan.size());
            congtrinh.setCongTrinhLienQuan(congTrinhLienQuan);
        }
        if (congtrinh.getThongTinKhac() != thongTinKhac) {
            lichSu.setThongTinKhac(congtrinh.getThongTinKhac() + " -> " + thongTinKhac);
            congtrinh.setThongTinKhac(thongTinKhac);
        }

        List<FileEntity> fileEntities = fileService.saveFileForCongtrinh(request.getFiles("files"));
        List<Image> images = imageService.storeImageForConstruction(request.getFiles("images"));
        CongTrinh ct = congTrinhService.updateCongTrinh(congtrinh, fileEntities, images);

        lichSu.setMaCT(maCT);
        lichSuChinhSuaCongTrinhRepository.save(lichSu);
        return ResponseEntity.ok(ct);
    }

    @GetMapping("/lich-su/{maCT}")
    public List<LichSuChinhSuaCongTrinh> getAllLichSuChinhSuaCongTrinh(@PathVariable String maCT) {
        return lichSuChinhSuaCongTrinhService.getAllLichSuChinhSuaCongTrinh(maCT);
    }

    @PostMapping("/delete-cong-trinh")
    public void deleteCongTrinhByMaCT(@RequestBody String maCT) {
        congTrinhService.deleteCongTrinhByMaCT(maCT);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<Object> uploadImageByMaCT(@RequestParam("maCT") String maCT,
                                      MultipartHttpServletRequest request) throws Exception {
        CongTrinh congtrinh = congTrinhService.findCongTrinhByMaCT(maCT);
        List<Image> images = imageService.storeImageForConstruction(request.getFiles("images"));
        CongTrinh ct = congTrinhService.updateCongTrinh(congtrinh, new ArrayList<>(), images);
        return ResponseEntity.ok(ct);
    }

    @PostMapping("/upload-file")
    public ResponseEntity<Object> uploadFileByMaCT(@RequestParam("maCT") String maCT,
                                                        MultipartHttpServletRequest request) throws Exception {
        CongTrinh congtrinh = congTrinhService.findCongTrinhByMaCT(maCT);
        List<FileEntity> files = fileService.saveFileForCongtrinh(request.getFiles("files"));
        CongTrinh ct = congTrinhService.updateCongTrinh(congtrinh, files, new ArrayList<>());
        return ResponseEntity.ok(ct);
    }

    @PostMapping("/delete-file")
    public ResponseEntity<Object> deleteFileByMaCT(@RequestParam("maCT") String maCT,
                                                   @RequestParam("fileName") String fileName) throws Exception {
        CongTrinh ct = congTrinhService.deleteFileByMaCT(maCT, fileName);
        return ResponseEntity.ok(ct);
    }

    @GetMapping("/get/excel")
    public void downloadExcelThongTinHoSoCongTrinh(HttpServletResponse response, @RequestParam String so) throws IOException {
        excelService.downloadExcelThongTinHoSoCongTrinh(response, so);
    }

    @GetMapping("/excel/bao-cao-tien-do")
    public void downloadExcelBaoCaoTienDo(HttpServletResponse response, @RequestParam String so, @RequestParam String maCT) throws IOException {
        excelService.downloadExcelBaoCaoTienDo(response, so, maCT);
    }

    @GetMapping("/excel/ho-so")
    public void getHoSoExcel(@RequestParam String donViThucHien, HttpServletResponse response) throws IOException {
        congTrinhService.getHoSoExcel(donViThucHien, response);
    }

    @GetMapping("/all")
    public List<CongTrinh> getAllCongTrinh() {
        return congTrinhService.getAllCongTrinh();
    }

    @PostMapping("/update/thong-tin")
    public void getAllCongTrinh(@RequestBody CongTrinh congTrinh) {
        congTrinhService.save(congTrinh);
    }

    @GetMapping("/maCT/{maCT}")
    public CongTrinh getCongTrinhByMaCT(@PathVariable String maCT) {
        return congTrinhService.getCongTrinhByMaCT(maCT);
    }
}
