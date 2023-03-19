package com.dado.quanlytailieu.repository;

import com.dado.quanlytailieu.enums.ConstructionType;
import com.dado.quanlytailieu.entity.Construction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConstructionRepository extends JpaRepository<Construction, Long> {
    List<Construction> getConstructionsByTypeOrderById(ConstructionType type);
}
