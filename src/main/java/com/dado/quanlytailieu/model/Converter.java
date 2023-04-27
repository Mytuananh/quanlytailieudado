package com.dado.quanlytailieu.model;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.function.Function;

@SuppressWarnings("unchecked")
@RequiredArgsConstructor
public enum Converter {
    INT(o -> o == null ? 0 : ((Number) o).intValue()),
    DATE(o -> LocalDate.parse((String) o));

    private final Function<Object, ?> func;

    public <T> T convert(Object obj) {
        return (T) func.apply(obj);
    }
}
