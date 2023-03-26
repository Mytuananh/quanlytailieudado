package com.dado.quanlytailieu.enums;

import lombok.Getter;

@Getter
public enum CongTrinhType {

    CONG("C"),
    KENH_DAT("K"),
    TRAM_BOM("TB"),
    KENH_XAY("CTTK"),
    KENH_TUOI("KT"),
    ;

    private final String value;
    CongTrinhType(String value) {
        this.value = value;
    }
}
