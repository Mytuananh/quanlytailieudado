package com.dado.quanlytailieu.model;

import org.springframework.data.domain.Page;

public interface PageInfo<T> {

    Page<T> page();

    String text();
}
