package com.dado.quanlytailieu.repository;

import com.dado.quanlytailieu.entity.LichSuHoSoCongTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LichSuHoSoCongTrinhRepository extends JpaRepository<LichSuHoSoCongTrinh, Long> {
}
