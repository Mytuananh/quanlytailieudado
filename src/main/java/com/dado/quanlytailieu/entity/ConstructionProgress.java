package com.dado.quanlytailieu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ConstructionProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
