package com.dado.quanlytailieu.entity;

import com.dado.quanlytailieu.enums.QuanLyTaiSanType;
import com.dado.quanlytailieu.enums.CongTrinhType;
import com.dado.quanlytailieu.extra.ListLongJsonType;
import com.dado.quanlytailieu.extra.MapStringStringJsonType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CongTrinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "ma_ct")
    String maCT;        // type + STT
    String name;
    String viTri;    // vi tri / dia diem
    String quyMo;        // quy mo
    String thietBi;

    @Type(ListLongJsonType.class)
    @Column(columnDefinition = "jsonb")
    List<Long> congTrinhLienQuan;

    String thongTinKhac;

    @Type(ListLongJsonType.class)
    @Column(columnDefinition = "jsonb")
    List<Long> images = new ArrayList<>();

    @Type(ListLongJsonType.class)
    @Column(columnDefinition = "jsonb")
    List<Long> files = new ArrayList<>();

    QuanLyTaiSanType quanLyTaiSanType;    // Quan ly tai san

    @ManyToMany
    @JoinTable(
            name = "user_congtrinh",
            joinColumns = @JoinColumn(name = "congtrinh_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<User> users = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "congTrinh")
    List<ViPhamLanChiem> viPhamLanChiem;              // Vi pham lan chiem

    String lichSuCT;

    @JsonIgnore
    @OneToMany(mappedBy = "congTrinh")
    List<HoSoCongTrinh> hoSoCongTrinhs = new ArrayList<>();   // chinh la ho so lich su cua cong trinh

    String description;

    CongTrinhType type;

    String latitude;

    String longitude;

}
