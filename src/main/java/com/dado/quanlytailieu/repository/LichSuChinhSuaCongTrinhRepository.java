package com.dado.quanlytailieu.repository;

import com.dado.quanlytailieu.entity.LichSuChinhSuaCongTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichSuChinhSuaCongTrinhRepository extends JpaRepository<LichSuChinhSuaCongTrinh, Long> {
    List<LichSuChinhSuaCongTrinh> findAllByMaCT(String maCT);
}
