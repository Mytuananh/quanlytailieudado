package com.dado.quanlytailieu.enums;

import lombok.Getter;

@Getter
public enum QuanLyTaiSanType {
    CONG_TY_DA_DO("Công ty Đa Độ"),
    SO_NNPTNT("Sở NNPTNT");

    private String value;

    QuanLyTaiSanType(String value) {
        this.value = value;
    }
}
