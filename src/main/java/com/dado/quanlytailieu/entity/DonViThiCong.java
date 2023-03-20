package com.dado.quanlytailieu.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class DonViThiCong {             // Don vi thi cong
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "donViThiCong")
    List<HoSoCongTrinh> hoSoCongTrinhList;
}
