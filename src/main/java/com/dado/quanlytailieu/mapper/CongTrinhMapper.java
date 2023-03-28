package com.dado.quanlytailieu.mapper;

import com.dado.quanlytailieu.command.QuanLyCongTrinhRequest;
import com.dado.quanlytailieu.dto.QuanLyCongTrinhDTO;
import com.dado.quanlytailieu.entity.CongTrinh;

import java.util.List;

public class CongTrinhMapper {
    public static CongTrinh toCongTrinh(QuanLyCongTrinhRequest request, List<CongTrinh> congTrinhList) {
        return CongTrinh.builder().maCT(request.getMaCT())
                .name(request.getName())
                .viTri(request.getViTri())
                .type(request.getType())
                .quyMo(request.getQuyMo())
                .thietBi(request.getThietBi())
                .thongTinKhac(request.getThongTinKhac())
                .build();
    }

    public static QuanLyCongTrinhDTO toQuanLyCongTrinhDTO(CongTrinh ct, List<String> tenCongTrinhLienQuan) {
        return QuanLyCongTrinhDTO.builder()
                .maCT(ct.getMaCT())
                .name(ct.getName())
                .viTri(ct.getViTri())
                .type(ct.getType())
                .quyMo(ct.getQuyMo())
                .thietBi(ct.getThietBi())
                .congTrinhLienQuan(ct.getCongTrinhLienQuan())
                .tenCongTrinhLienQuan(tenCongTrinhLienQuan)
                .thongTinKhac(ct.getThongTinKhac())
                .images(ct.getImages())
                .files(ct.getFiles())
                .build();
    }

    public static QuanLyCongTrinhDTO toQuanLyCongTrinhDTO(CongTrinh ct) {
        return QuanLyCongTrinhDTO.builder()
                .maCT(ct.getMaCT())
                .name(ct.getName())
                .viTri(ct.getViTri())
                .type(ct.getType())
                .quyMo(ct.getQuyMo())
                .thietBi(ct.getThietBi())
                .congTrinhLienQuan(ct.getCongTrinhLienQuan())
                .thongTinKhac(ct.getThongTinKhac())
                .images(ct.getImages())
                .files(ct.getFiles())
                .build();
    }
}
