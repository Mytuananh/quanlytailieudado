package com.dado.quanlytailieu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NguonVonEnum {

    TSCD(1),
    SPDVTL(2),
    NGUON_VON_DIEU_LE(3),
    NGUON_KHAC(4),
    ;

    private final int intValue;
}
