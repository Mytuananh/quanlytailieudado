package com.dado.quanlytailieu.entity;

import com.dado.quanlytailieu.enums.CongTrinhType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LichSuChinhSuaCongTrinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String maCT;
    String name;
    String viTri;
    String type;
    String quyMo;
    String thietBi;
    String congTrinhLienQuan;
    String thongTinKhac;
    String image;
    String file;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime editTime = LocalDateTime.now();
}
