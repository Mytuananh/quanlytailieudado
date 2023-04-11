package com.dado.quanlytailieu.extra;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class ListStringJsonType extends AbstractJsonType<List<String>> {
    public ListStringJsonType() {
        super(new TypeReference<>() {}, ArrayList::new);
    }
}
