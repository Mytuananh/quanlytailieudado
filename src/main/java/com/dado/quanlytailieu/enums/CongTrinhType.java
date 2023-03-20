package com.dado.quanlytailieu.enums;

import lombok.Getter;

@Getter
public enum CongTrinhType {

    CONG("cong"),
    KENH_DAT("kenh_dat"),
    TRAM_BOM("tram_bom"),
    KENH_XAY("kenh_xay"),
    KENH_TUOI("kenh_tuoi"),
    ;

    private final String value;
    CongTrinhType(String value) {
        this.value = value;
    }
}
