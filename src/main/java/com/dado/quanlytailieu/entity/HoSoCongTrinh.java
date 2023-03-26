package com.dado.quanlytailieu.entity;

import com.dado.quanlytailieu.enums.TinhTrangTrienKhai;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class HoSoCongTrinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String code;        //year.STTKH.codeCT-Luu-TrangThai

    @ManyToOne
    @JoinColumn(name = "construction_id")
    CongTrinh congTrinh;

    String name;

    String content;     // Noi dung sua chua, nang cap

    @OneToMany
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    List<FileEntity> files = new ArrayList<>();     // Ho so phap ly

    @ManyToOne
    DonViThiCong donViThiCong;      // Don vi thi cong

    LocalDate thoiGianThiCongFrom;     // thoi gian thi cong
    LocalDate thoiGianThiCongTo;

    TinhTrangTrienKhai tinhTrangTrienKhai;      // Tình trang trien khai

    @OneToOne
    @JoinColumn(name = "tien_do_ho_so_id", referencedColumnName = "id")
    TienDoHoSo tienDoHoSo;      // Tien do ho so

    @OneToOne
    @JoinColumn(name = "tien_do_thi_cong_id", referencedColumnName = "id")
    TienDoThiCong tienDoThiCong;

    String duToan;

    @OneToOne
    @JoinColumn(name = "du_toan_file_id",  referencedColumnName = "id")
    FileEntity duToanFile;

    String quyetToanAB;      // Quyet toan

    @OneToOne
    @JoinColumn(name = "quyet_toan_AB_file_id",  referencedColumnName = "id")
    FileEntity quyetToanABFile;

    String quyetToan;      // Quyet toan

    @OneToOne
    @JoinColumn(name = "quyet_toan_id",  referencedColumnName = "id")
    FileEntity quyetToanFile;

    @OneToOne
    @JoinColumn(name = "bao_cao_bien_phong_file_id",  referencedColumnName = "id")
    FileEntity baoCaoBienPhongFile;        // Bao cao bien phong

    String comment;

    @OneToOne
    @JoinColumn(name = "created_user_id",  referencedColumnName = "id")
    User createdUser;

    @CreatedDate()
    LocalDateTime createdTime = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "updated_user_id",  referencedColumnName = "id")
    User updatedUser;

    @LastModifiedDate
    LocalDateTime updatedTime;

}
