package com.dado.quanlytailieu.enums;

import lombok.Getter;

@Getter
public enum DeploymentStatus {
    ONGOING("Đang thục hiện"),
    FINISHED("Hoàn thành");

    private String value;

    DeploymentStatus(String value) {
        this.value = value;
    }
}
