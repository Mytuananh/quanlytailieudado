package com.dado.quanlytailieu.util;

import com.dado.quanlytailieu.model.AmountPageInfo;
import com.dado.quanlytailieu.model.PageInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class QueryHelper {
    private final EntityManager entityManager;

    public <T> Optional<T> findFirstResult(String sql, Map<String, Object> params, Class<T> type) {
        var query = entityManager.createQuery(sql, type);
        params.forEach(query::setParameter);
        try {
            return Optional.of(query.setMaxResults(1).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public <T> List<T> findAll(String sql, Map<String, Object> params, Class<T> type) {
        var query = entityManager.createQuery(sql, type);
        params.forEach(query::setParameter);
        return query.getResultList();
    }

    public static <R> PageInfo<R> getPageInfo(Pageable pageable, Supplier<TypedQuery<R>> contentSupplier,
                                              Supplier<TypedQuery<Object[]>> countAndTotalAmountSupplier) {
        var countAndTotalAmount = countAndTotalAmountSupplier.get().getSingleResult();
        var page = getPage(pageable, (Long) countAndTotalAmount[0], contentSupplier);
        return new AmountPageInfo<>(page, (Long) countAndTotalAmount[1]);
    }

    public static <R> Page<R> getPage(Pageable pageable, long total, Supplier<TypedQuery<R>> contentSupplier) {
        List<R> content;
        if (total == 0) {
            content = Collections.emptyList();
        } else {
            content = contentSupplier.get()
                    .setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();
        }
        return new PageImpl<>(content, pageable, total);
    }
}
