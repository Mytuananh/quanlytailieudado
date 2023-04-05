package com.dado.quanlytailieu.repository;

import com.dado.quanlytailieu.entity.TienDoThiCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TienDoThiCongRepository extends JpaRepository<TienDoThiCong, Long> {

    List<TienDoThiCong> findAllByMaCT(String maCT);
}
