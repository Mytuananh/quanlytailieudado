package com.dado.quanlytailieu.repository;

import com.dado.quanlytailieu.dto.CongTrinhDTO;
import com.dado.quanlytailieu.enums.CongTrinhType;
import com.dado.quanlytailieu.entity.CongTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CongTrinhRepository extends JpaRepository<CongTrinh, Long> {
    List<CongTrinh> getCongTrinhByTypeOrderById(CongTrinhType type);
    List<CongTrinh> getCongTrinhsByMaCTIsIn(List<String> maCTList);
    List<CongTrinh> getCongTrinhsByIdIsIn(List<Long> congTrinhLienQuan);
    List<CongTrinh> getCongTrinhsByNameIsIn(List<String> congTrinhLienQuan);

    Integer countCongTrinhsByType(CongTrinhType type);

    CongTrinh getCongTrinhByMaCT(String maCT);
    List<CongTrinh> getCongTrinhsByDonViThucHien(String donViThucHien);

}
