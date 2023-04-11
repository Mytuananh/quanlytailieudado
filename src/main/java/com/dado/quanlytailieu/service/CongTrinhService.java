package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.command.QuanLyCongTrinhRequest;
import com.dado.quanlytailieu.dto.CongTrinhCountDto;
import com.dado.quanlytailieu.dto.CongTrinhDTO;
import com.dado.quanlytailieu.dto.QuanLyCongTrinhDTO;
import com.dado.quanlytailieu.entity.FileEntity;
import com.dado.quanlytailieu.entity.Image;
import com.dado.quanlytailieu.enums.CongTrinhType;
import com.dado.quanlytailieu.entity.CongTrinh;
import com.dado.quanlytailieu.mapper.CongTrinhMapper;
import com.dado.quanlytailieu.repository.CongTrinhRepository;
import com.dado.quanlytailieu.repository.FileRepository;
import com.dado.quanlytailieu.repository.HoSoCongTrinhRepository;
import com.dado.quanlytailieu.repository.ImageRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private FileRepository fileRepository;


    public CongTrinh createCongTrinh(CongTrinh congTrinhRequest, List<FileEntity> fileEntities, List<Image> images) {
        Integer count = congTrinhRepository.countCongTrinhsByType(congTrinhRequest.getType()) + 1;
        if (congTrinhRequest.getMaCT() == null) {
            congTrinhRequest.setMaCT(String.format("%s%d", congTrinhRequest.getType().getValue(), count));
        }
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

    public List<CongTrinhDTO> getConstructionByType(CongTrinhType type) {
        List<CongTrinh> congTrinhs = congTrinhRepository.getCongTrinhByTypeOrderById(type);
        return congTrinhs.stream().map(CongTrinhMapper::toCongTrinhDTO).toList();
    }

    public QuanLyCongTrinhDTO getDataQuanLyCongTrinhByMaCT(String maCT) {
        CongTrinh ct = congTrinhRepository.getCongTrinhByMaCT(maCT);
        List<String> tenCongTrinhLienQuan = congTrinhRepository.getCongTrinhsByMaCTIsIn(ct.getCongTrinhLienQuan()).stream().map(CongTrinh::getName).toList();
        return CongTrinhMapper.toQuanLyCongTrinhDTO(ct, tenCongTrinhLienQuan);
    }

    public CongTrinh findCongTrinhByMaCT(String maCT) {
        return congTrinhRepository.getCongTrinhByMaCT(maCT);
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

    public List<Long> getListImageByMaCT(String maCT) {
        return congTrinhRepository.getCongTrinhByMaCT(maCT).getImages();
    }

    public List<String> getListFileByMaCT(String maCT) {
        List<Long> fileIds = congTrinhRepository.getCongTrinhByMaCT(maCT).getFiles();
        return fileRepository.findFileEntitiesByIdIsIn(fileIds).stream().map(FileEntity::getFileName).toList();
    }

    public void deleteCongTrinhByMaCT(String maCT) {
        CongTrinh ct = congTrinhRepository.getCongTrinhByMaCT(maCT);
        congTrinhRepository.delete(ct);
    }

    public CongTrinh deleteFileByMaCT(String maCT, String fileName) {
        CongTrinh ct = congTrinhRepository.getCongTrinhByMaCT(maCT);
        FileEntity file = fileRepository.findByFileName(fileName);
        ct.getFiles().remove(file.getId());

        fileRepository.delete(file);
        return congTrinhRepository.save(ct);
    }

    public void getHoSoExcel(String donViThucHien, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        // tạo sheet mới
        Sheet sheet = workbook.createSheet("Danh sách");

// tạo header cho sheet
        Row header = sheet.createRow(5);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        header.createCell(0).setCellValue("STT");
        header.getCell(0).setCellStyle(headerStyle);
        header.createCell(1).setCellValue("Name");
        header.getCell(1).setCellStyle(headerStyle);
        header.createCell(2).setCellValue("Age");
        header.getCell(2).setCellStyle(headerStyle);

        // lấy dữ liệu từ cơ sở dữ liệu
        var congTrinhList = congTrinhRepository.getCongTrinhsByDonViThucHien(donViThucHien);

        // ghi dữ liệu vào sheet
        int rowNum = 6;
        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataCellStyle.setBorderTop(BorderStyle.THIN);
        dataCellStyle.setBorderRight(BorderStyle.THIN);
        dataCellStyle.setBorderBottom(BorderStyle.THIN);
        dataCellStyle.setBorderLeft(BorderStyle.THIN);
//        for (CongTrinh ct : congTrinhList) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(rs.getInt("id"));
//            row.getCell(0).setCellStyle(dataCellStyle);
//            row.createCell(1).setCellValue(rs.getString("name"));
//            row.getCell(1).setCellStyle(dataCellStyle);
//            row.createCell(2).setCellValue(rs.getInt("age"));
//            row.getCell(2).setCellStyle(dataCellStyle);
//        }

        // merge và center dòng A10 đến E10
        CellStyle centerCellStyle = workbook.createCellStyle();
        centerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        centerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        centerCellStyle.setBorderTop(BorderStyle.THIN);
        centerCellStyle.setBorderRight(BorderStyle.THIN);
        centerCellStyle.setBorderBottom(BorderStyle.THIN);
        centerCellStyle.setBorderLeft(BorderStyle.THIN);

        sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 4));
        Row donViThucHienRow = sheet.createRow(9);
        Cell donViThucHienCell = donViThucHienRow.createCell(0);
        donViThucHienCell.setCellValue("DON VI THUC HIEN");
        donViThucHienCell.setCellStyle(centerCellStyle);

        // tạo file Excel
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=DanhSach.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();

    }

    public CongTrinhCountDto getAllCongTrinhCount() {
        CongTrinhCountDto dto = new CongTrinhCountDto();
        dto.setCongDuoiDe(congTrinhRepository.countCongTrinhsByType(CongTrinhType.CONG));
        dto.setTramBom(congTrinhRepository.countCongTrinhsByType(CongTrinhType.TRAM_BOM));
        dto.setCongTrinhTrenKenh(congTrinhRepository.countCongTrinhsByType(CongTrinhType.KENH_XAY));
        dto.setKenhCapI(congTrinhRepository.countCongTrinhsByType(CongTrinhType.KENH_DAT));
        dto.setKenhTuoiSauTB(congTrinhRepository.countCongTrinhsByType(CongTrinhType.KENH_TUOI));
        return dto;

    }

    public List<CongTrinh> getAllCongTrinh() {
         return congTrinhRepository.findAll();
    }

    public void save(CongTrinh congTrinh) {
        congTrinhRepository.save(congTrinh);
    }

    public CongTrinh getCongTrinhByMaCT(String maCT) {
        return congTrinhRepository.getCongTrinhByMaCT(maCT);
    }
}
