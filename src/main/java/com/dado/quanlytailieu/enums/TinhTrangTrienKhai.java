package com.dado.quanlytailieu.enums;

import lombok.Getter;

@Getter
public enum TinhTrangTrienKhai {
    ONGOING("Đang thục hiện"),
    FINISHED("Hoàn thành");

    private String value;

    TinhTrangTrienKhai(String value) {
        this.value = value;
    }
}
