package com.dado.quanlytailieu.entity;

import com.dado.quanlytailieu.enums.AssetManagementType;
import com.dado.quanlytailieu.enums.ConstructionType;
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
public class Construction {
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
    Construction relatedConstruction;

    String otherInformation;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    List<Image> images = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    List<FileEntity> files;

    AssetManagementType assetManagementType;    // Quan ly tai san

    @ManyToMany(mappedBy = "constructions")
    List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "construction")
    List<Violation> violation;              // Vi pham lan chiem

    String history;

    @JsonIgnore
    @OneToMany(mappedBy = "construction")
    List<ConstructionDocument> constructionDocuments = new ArrayList<>();   // chinh la ho so lich su cua cong trinh

    String description;

    ConstructionType type;

}
