package com.dado.quanlytailieu.extra;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class ListLongJsonType extends AbstractJsonType<List<String>>{

    public ListLongJsonType() {
        super(new TypeReference<>() {}, ArrayList::new);
    }
}
