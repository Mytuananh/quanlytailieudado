package com.dado.quanlytailieu.entity;

import com.dado.quanlytailieu.enums.HoSoCTType;
import com.dado.quanlytailieu.enums.NguonVonEnum;
import com.dado.quanlytailieu.enums.TinhTrangTrienKhai;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

//    @ManyToOne
//    DonViThiCong donViThiCong;      // Don vi thi cong

    LocalDate thoiGianThiCongFrom;     // thoi gian thi cong
    LocalDate thoiGianThiCongTo;

    TinhTrangTrienKhai tinhTrangTrienKhai;      // Tình trang trien khai

    @OneToOne
    @JoinColumn(name = "tien_do_ho_so_id", referencedColumnName = "id")
    TienDoHoSo tienDoHoSo;      // Tien do ho so

//    @OneToOne
//    @JoinColumn(name = "tien_do_thi_cong_id", referencedColumnName = "id")
//    TienDoThiCong tienDoThiCong;

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

    String ctName;
    String maHS;
    String nameHS;
    String loaiHS;
    String namTaoHS;
    String namHoanThanhHS;
    String donViLapHS;

    // man 3b create/HoSoCT
    HoSoCTType hoSoCTType;
    String diaDiem;
    String latitude;
    String longitude;
    String namThucHien;
    String donViLapHoSO;
    NguonVonEnum nguonVonEnum;
    String quyetDinhPheDuyetDuToan;
    @OneToOne
    @JoinColumn(name = "file_quyetdinhpheduyetdutoan_id")
    FileEntity fileQuyetDinhPheDuyetDuToan;
    String giaTriDuDoan;
    String donViThiCong;
    LocalDate thoiGianThiCongStart;
    LocalDate thoiGianThiCongEnd;
    String tienDoThiCong;
    String quyetDinhPheDuyetQuyetToan;
    @OneToOne
    @JoinColumn(name = "file_quyetdinhpheduyetquyettoan_id")
    FileEntity fileQuyetDinhPheDuyetQuyetToan;
    String giaTriQuyetToan;
    String nguoiPhuTrach; // TODO: -> UserEntity
    String ghiChu;

    @OneToMany
    @JoinColumn(name = "file_hinh_anh_hs_ct_id",  referencedColumnName = "id")
    List<FileEntity> hinhAnhHoSoCT;

    @OneToOne
    @JoinColumn(name = "created_user_id",  referencedColumnName = "id")
    UserEntity createdUser;

    @CreatedDate()
    LocalDateTime createdTime = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "updated_user_id",  referencedColumnName = "id")
    UserEntity updatedUser;

    @LastModifiedDate
    LocalDateTime updatedTime;

}
