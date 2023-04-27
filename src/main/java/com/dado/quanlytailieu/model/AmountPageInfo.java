package com.dado.quanlytailieu.model;

import com.dado.quanlytailieu.util.DisplayUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record AmountPageInfo<T>(
        Page<T> page,
        Long totalAmount
) implements PageInfo<T> {

    public AmountPageInfo(List<T> content, Pageable pageable, Object[] countAndTotalAmount) {
        this(new PageImpl<>(content, pageable, (Long) countAndTotalAmount[0]), (Long) countAndTotalAmount[1]);
    }

    @Override
    public String text() {
        return "tương ứng <span class='text-success'>" + DisplayUtils.formatVnd(totalAmount.intValue()) + "</span>";
    }
}
