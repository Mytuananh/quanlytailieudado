package com.dado.quanlytailieu.dto;

import java.util.List;

import com.dado.quanlytailieu.entity.CongTrinh;
import com.dado.quanlytailieu.entity.FileEntity;
import com.dado.quanlytailieu.enums.CongTrinhType;
import lombok.*;
import org.springframework.core.io.Resource;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuanLyCongTrinhDTO {
    String maCT;
    String name;
    String viTri;
    CongTrinhType type;
    String quyMo;
    String thietBi;
    List<Long> congTrinhLienQuan;
    List<String> tenCongTrinhLienQuan;
    String thongTinKhac;
    List<Long> images;        // url
    List<Long> files;         // url
}
