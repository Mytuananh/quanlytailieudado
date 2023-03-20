package com.dado.quanlytailieu.entity;

import com.dado.quanlytailieu.enums.QuanLyTaiSanType;
import com.dado.quanlytailieu.enums.CongTrinhType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    String code;        // type + STT
    String name;
    String location;    // vi tri / dia diem
    String size;        // quy mo
    String device;

    @ManyToOne
    @JoinColumn(name = "related_construction_id", referencedColumnName = "id")
    CongTrinh relatedCongTrinh;

    String otherInformation;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    List<Image> images = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    List<FileEntity> files;

    QuanLyTaiSanType quanLyTaiSanType;    // Quan ly tai san

    @ManyToMany(mappedBy = "congTrinhs")
    List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "congTrinh")
    List<ViPhamLanChiem> viPhamLanChiem;              // Vi pham lan chiem

    String history;

    @JsonIgnore
    @OneToMany(mappedBy = "congTrinh")
    List<HoSoCongTrinh> hoSoCongTrinhs = new ArrayList<>();   // chinh la ho so lich su cua cong trinh

    String description;

    CongTrinhType type;

}
