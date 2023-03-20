package com.dado.quanlytailieu.repository;

import com.dado.quanlytailieu.enums.CongTrinhType;
import com.dado.quanlytailieu.entity.CongTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CongTrinhRepository extends JpaRepository<CongTrinh, Long> {
    List<CongTrinh> getCongTrinhByTypeOrderById(CongTrinhType type);
}
