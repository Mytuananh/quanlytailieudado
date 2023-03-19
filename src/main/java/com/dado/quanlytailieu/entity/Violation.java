package com.dado.quanlytailieu.entity;

import jakarta.persistence.*;

@Entity
public class Violation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Construction construction;

}
