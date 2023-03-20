package com.dado.quanlytailieu.repository;

import java.util.List;

import com.dado.quanlytailieu.entity.HoSoCongTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HoSoCongTrinhRepository extends JpaRepository<HoSoCongTrinh, Long> {
    @Query(value = "SELECT i FROM HoSoCongTrinh i where i.congTrinh.id=?1")
    List<HoSoCongTrinh> getConstructionDocsByConstructionId(String id);
}
