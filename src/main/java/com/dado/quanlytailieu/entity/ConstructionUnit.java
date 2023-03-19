package com.dado.quanlytailieu.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ConstructionUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "constructionUnit")
    List<ConstructionDocument> constructionDocumentList;
}
