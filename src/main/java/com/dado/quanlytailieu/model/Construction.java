package com.dado.quanlytailieu.model;

import com.dado.quanlytailieu.enums.ConstructionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    String name;
    String code;
    String location;    // vi tri
    ConstructionType type;
    String address;     // dia diem
    String area;

    @JsonIgnore
    @OneToMany(mappedBy = "construction",cascade = CascadeType.ALL)
    List<Image> images;

    @JsonIgnore
    @OneToMany(mappedBy = "construction")
    List<ConstructionDocument> constructionDocuments;
}
