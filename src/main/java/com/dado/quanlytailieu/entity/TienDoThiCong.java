package com.dado.quanlytailieu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class TienDoThiCong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String maCT;

    @CreationTimestamp
    LocalDateTime ngayThucHien;

    String noiDungCongViec;

    String tinhTrangNghiemThu;

    String chatLuongCongTrinh;
    String ghiChu;
}
