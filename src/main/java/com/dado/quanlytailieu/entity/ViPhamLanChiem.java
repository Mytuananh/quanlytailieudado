package com.dado.quanlytailieu.entity;

import jakarta.persistence.*;

@Entity
public class ViPhamLanChiem {    // Vi pham lan chiem
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    CongTrinh congTrinh;

}
