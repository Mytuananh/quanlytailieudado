package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.entity.CongTrinh;
import com.dado.quanlytailieu.entity.TienDoThiCong;
import com.dado.quanlytailieu.repository.CongTrinhRepository;
import com.dado.quanlytailieu.repository.TienDoThiCongRepository;
import com.dado.quanlytailieu.util.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class ExcelService {

    CongTrinhRepository congTrinhRepository;
    TienDoThiCongRepository tienDoThiCongRepository;

    public void downloadExcelThongTinHoSoCongTrinh(HttpServletResponse response, String so) throws IOException {
        // tạo workbook mới
        Workbook workbook = new XSSFWorkbook();
        // tạo sheet mới
        Sheet sheet = workbook.createSheet("Thông tin hồ sơ");

        CellStyle mergedCellStyle = ExcelUtil.getMergedCellStyle(workbook);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 4));
        Row r0 = sheet.createRow(0);
        Cell R0C1 = r0.createCell(1);
        R0C1.setCellValue("UBND THÀNH PHỐ HẢI PHÒNG");
        R0C1.setCellStyle(mergedCellStyle);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 10, 14));
        Cell CHXHCell = r0.createCell(10);
        CHXHCell.setCellValue("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM");
        CHXHCell.setCellStyle(mergedCellStyle);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));
        Row r1 = sheet.createRow(1);
        Cell r1c0 = r1.createCell(0);
        r1c0.setCellValue("CÔNG TY TNHH MTV KHAI THÁC CÔNG TRÌNH THỦY LỢI ĐA ĐỘ");
        r1c0.setCellStyle(mergedCellStyle);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 11, 13));
        Cell r1c11 = r1.createCell(11);
        r1c11.setCellValue("Độc lập - Tự do - Hạnh phúc");
        r1c11.setCellStyle(mergedCellStyle);

        sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 3));
        Row r2 = sheet.createRow(2);
        Cell r2c1 = r2.createCell(1);
        r2c1.setCellValue("Số " + so +" /BC-TLĐ");
        r2c1.setCellStyle(mergedCellStyle);

//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 8, 8));
        Row r3 = sheet.createRow(3);
        Cell r3c8 = r3.createCell(8);
        r3c8.setCellValue("BÁO CÁO");
        r3c8.setCellStyle(mergedCellStyle);

        sheet.addMergedRegion(new CellRangeAddress(4, 4, 6, 10));
        Row r4= sheet.createRow(4);
        Cell r4c6 = r4.createCell(6);
        r4c6.setCellValue("TỔNG HỢP TÌNH HÌNH THỰC HIỆN CÁC CÔNG TRÌNH");
        r4c6.setCellStyle(mergedCellStyle);

        // tạo header cho sheet
        Row header = sheet.createRow(5);
        CellStyle headerStyle = ExcelUtil.getHeaderTableStyle(workbook);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        header.createCell(0).setCellValue("STT");
        header.createCell(1).setCellValue("Mã công trình");
        header.createCell(2).setCellValue("Tên công trình");
        header.createCell(3).setCellValue("Năm thực hiện");
        header.createCell(4).setCellValue("Đơn vị thực hiện");
        header.createCell(5).setCellValue("Nội dung, quy mô sửa chữa");
        header.createCell(6).setCellValue("Nguồn vốn");
        header.createCell(7).setCellValue("Số, ngày tháng quyết định phê duyệt dự toán");
        header.createCell(8).setCellValue("Giá trị dự toán");
        header.createCell(9).setCellValue("Đơn vị thi công");
        header.createCell(10).setCellValue("Thời gian thi công");
        header.createCell(11).setCellValue("Tiến độ thi công thực tế");
        header.createCell(12).setCellValue("Số, ngày tháng quyết định phê duyệt quyết toán");
        header.createCell(13).setCellValue("Giá trị quyết toán");
        header.createCell(14).setCellValue("Người phụ trách");
        header.createCell(15).setCellValue("Ghi chú");
        for (int i = 0; i <= 15 ; i++) {
            header.getCell(i).setCellStyle(headerStyle);
        }

        List<CongTrinh> congTrinhList = congTrinhRepository.findAll();

        // ghi dữ liệu vào sheet
        int rowNum = 6;
        CellStyle dataCellStyle = ExcelUtil.getBodyTableStyle(workbook);
        for (int i = 0; i < congTrinhList.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(congTrinhList.get(i).getMaCT());
            row.createCell(2).setCellValue(congTrinhList.get(i).getName());
            row.createCell(3).setCellValue(congTrinhList.get(i).getName());
            row.createCell(4).setCellValue(congTrinhList.get(i).getDonViThucHien());
            row.createCell(5).setCellValue(congTrinhList.get(i).getName());
            row.createCell(6).setCellValue(congTrinhList.get(i).getName());
            row.createCell(7).setCellValue(congTrinhList.get(i).getName());
            row.createCell(8).setCellValue(congTrinhList.get(i).getName());
            row.createCell(9).setCellValue(congTrinhList.get(i).getName());
            row.createCell(10).setCellValue(congTrinhList.get(i).getName());
            row.createCell(11).setCellValue(congTrinhList.get(i).getName());
            row.createCell(12).setCellValue(congTrinhList.get(i).getName());
            row.createCell(13).setCellValue(congTrinhList.get(i).getName());
            row.createCell(14).setCellValue(congTrinhList.get(i).getName());
            row.createCell(15).setCellValue(congTrinhList.get(i).getDescription());
            // TODO: sua lai cac truong
            for (int j = 0; j <= 15 ; j++) {
                row.getCell(j).setCellStyle(dataCellStyle);
            }
        }

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 12, 14));
        Row r20= sheet.createRow(rowNum);
        Cell c12 = r20.createCell(12);
        c12.setCellValue("Đơn vị thực hiện: ");
        // TODO: sua thanh don vi thuc hien
        c12.setCellStyle(mergedCellStyle);

        // tạo file Excel
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=Thong-tin-ho-so.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    public void downloadExcelBaoCaoTienDo(HttpServletResponse response, String so, String maCT) throws IOException {
        CongTrinh congTrinh = congTrinhRepository.getCongTrinhByMaCT(maCT);

        // tạo workbook mới
        Workbook workbook = new XSSFWorkbook();
        // tạo sheet mới
        Sheet sheet = workbook.createSheet("Thông tin hồ sơ");

        CellStyle mergedCellStyle = ExcelUtil.getMergedCellStyle(workbook);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 4));
        Row r0 = sheet.createRow(0);
        Cell R0C1 = r0.createCell(1);
        R0C1.setCellValue("UBND THÀNH PHỐ HẢI PHÒNG");
        R0C1.setCellStyle(mergedCellStyle);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 10, 14));
        Cell CHXHCell = r0.createCell(10);
        CHXHCell.setCellValue("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM");
        CHXHCell.setCellStyle(mergedCellStyle);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));
        Row r1 = sheet.createRow(1);
        Cell r1c0 = r1.createCell(0);
        r1c0.setCellValue("CÔNG TY TNHH MTV KHAI THÁC CÔNG TRÌNH THỦY LỢI ĐA ĐỘ");
        r1c0.setCellStyle(mergedCellStyle);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 11, 13));
        Cell r1c11 = r1.createCell(11);
        r1c11.setCellValue("Độc lập - Tự do - Hạnh phúc");
        r1c11.setCellStyle(mergedCellStyle);

        sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 3));
        Row r2 = sheet.createRow(2);
        Cell r2c1 = r2.createCell(1);
        r2c1.setCellValue("Số " + so +" /BC-TLĐ");
        r2c1.setCellStyle(mergedCellStyle);

//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 8, 8));
        Row r3 = sheet.createRow(3);
        Cell r3c8 = r3.createCell(8);
        r3c8.setCellValue("BÁO CÁO");
        r3c8.setCellStyle(mergedCellStyle);

        sheet.addMergedRegion(new CellRangeAddress(4, 4, 6, 10));
        Row r4= sheet.createRow(4);
        Cell r4c6 = r4.createCell(6);
        r4c6.setCellValue("TIẾN ĐỘ THI CÔNG CÔNG TRÌNH");
        r4c6.setCellStyle(mergedCellStyle);

        Row r5= sheet.createRow(5);
        Cell r5c1 = r4.createCell(1);
        r4c6.setCellValue("Mã công trình: " + maCT);

        Row r6= sheet.createRow(6);
        Cell r6c1 = r4.createCell(1);
        r4c6.setCellValue("Tên công trình: " + congTrinh.getName());

        Row r7= sheet.createRow(7);
        Cell r7c1 = r4.createCell(1);
        r4c6.setCellValue("Thời gian thực hiện theo hợp đồng: " + congTrinh.getName());

        // tạo header cho sheet
        Row header = sheet.createRow(8);
        CellStyle headerStyle = ExcelUtil.getHeaderTableStyle(workbook);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        header.createCell(0).setCellValue("STT");
        header.createCell(1).setCellValue("Ngày tháng năm");
        header.createCell(2).setCellValue("Nội dung công việc thực hiện");
        header.createCell(3).setCellValue("Tình trạng nghiệm thu");
        header.createCell(4).setCellValue("Chất lượng công trình");
        header.createCell(5).setCellValue("Ghi chú");
        for (int i = 0; i <= 5 ; i++) {
            header.getCell(i).setCellStyle(headerStyle);
        }

        List<TienDoThiCong> congTrinhList = tienDoThiCongRepository.findAllByMaCT(maCT);

        // ghi dữ liệu vào sheet
        int rowNum = 9;
        CellStyle dataCellStyle = ExcelUtil.getBodyTableStyle(workbook);
        for (int i = 0; i < congTrinhList.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(congTrinhList.get(i).getNgayThucHien());
            row.createCell(2).setCellValue(congTrinhList.get(i).getNoiDungCongViec());
            row.createCell(3).setCellValue(congTrinhList.get(i).getTinhTrangNghiemThu());
            row.createCell(4).setCellValue(congTrinhList.get(i).getChatLuongCongTrinh());
            row.createCell(5).setCellValue(congTrinhList.get(i).getGhiChu());
            // TODO: sua lai cac truong
            for (int j = 0; j <= 5 ; j++) {
                row.getCell(j).setCellStyle(dataCellStyle);
            }
        }

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 12, 14));
        Row r20= sheet.createRow(rowNum);
        Cell c12 = r20.createCell(12);
        c12.setCellValue("Người quản lý: " + congTrinh.getNguoiQuanLy());
        // TODO: sua thanh don vi thuc hien
        c12.setCellStyle(mergedCellStyle);

        // tạo file Excel
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=BC-Tien-Do.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    public void demo(HttpServletResponse response) throws IOException {
        // tạo workbook mới
        Workbook workbook = new XSSFWorkbook();
        // tạo sheet mới
        Sheet sheet = workbook.createSheet("Danh sách");

        // tạo header cho sheet
        Row header = sheet.createRow(5);
        CellStyle headerStyle = ExcelUtil.getHeaderTableStyle(workbook);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        header.createCell(0).setCellValue("STT");
        header.getCell(0).setCellStyle(headerStyle);
        header.createCell(1).setCellValue("Name");
        header.getCell(1).setCellStyle(headerStyle);
        header.createCell(2).setCellValue("Age");
        header.getCell(2).setCellStyle(headerStyle);

        List<CongTrinh> congTrinhList = congTrinhRepository.findAll();

        // ghi dữ liệu vào sheet
        int rowNum = 6;
        CellStyle dataCellStyle = ExcelUtil.getBodyTableStyle(workbook);
        for (CongTrinh ct : congTrinhList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ct.getId());
            row.getCell(0).setCellStyle(dataCellStyle);
            row.createCell(1).setCellValue(ct.getName());
            row.getCell(1).setCellStyle(dataCellStyle);
            row.createCell(2).setCellValue(ct.getMaCT());
            row.getCell(2).setCellStyle(dataCellStyle);
        }

        // merge và center dòng A10 đến E10
        CellStyle centerCellStyle = ExcelUtil.getCenterBorderThinCellStyle(workbook);

        CellStyle mergedCellStyle = workbook.createCellStyle();
        mergedCellStyle.cloneStyleFrom(centerCellStyle);
        mergedCellStyle.setBorderTop(BorderStyle.MEDIUM);
        mergedCellStyle.setBorderRight(BorderStyle.MEDIUM);
        mergedCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        mergedCellStyle.setBorderLeft(BorderStyle.MEDIUM);

        sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 4));
        Row donViThucHienRow = sheet.createRow(9);
        Cell donViThucHienCell = donViThucHienRow.createCell(0);
        donViThucHienCell.setCellValue("DON VI THUC HIEN");
        donViThucHienCell.setCellStyle(mergedCellStyle);

        // tạo file Excel
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=DanhSach.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
