package com.dado.quanlytailieu.enums;

import lombok.Getter;

@Getter
public enum TrangThaiCongTrinh {
    DA("Đã"),
    CHUA_CAM_MOC("Chưa cắm mốc");

    private String value;

    TrangThaiCongTrinh(String value) {
        this.value = value;
    }
}
