package com.dado.quanlytailieu.enums;

import lombok.Getter;

@Getter
public enum ConstructionStatus {
    DA("Đã"),
    CHUA_CAM_MOC("Chưa cắm mốc");

    private String value;

    ConstructionStatus(String value) {
        this.value = value;
    }
}
