package com.dado.quanlytailieu.enums;

import lombok.Getter;

@Getter
public enum AssetManagementType {
    CONG_TY_DA_DO("Công ty Đa Độ"),
    SO_NNPTNT("Sở NNPTNT");

    private String value;

    AssetManagementType(String value) {
        this.value = value;
    }
}
