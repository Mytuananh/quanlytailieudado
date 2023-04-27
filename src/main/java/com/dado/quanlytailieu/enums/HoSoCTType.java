package com.dado.quanlytailieu.enums;

public enum HoSoCTType {

    SUA_CHUA_THUONG_XUYEN(1),
    SUA_CHUA_LON(2),
    DU_AN(3)
    ;

    private final int intValue;

    HoSoCTType(int intValue) {
        this.intValue = intValue;
    }
}
