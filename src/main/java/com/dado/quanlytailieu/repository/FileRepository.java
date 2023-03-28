package com.dado.quanlytailieu.repository;

import com.dado.quanlytailieu.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findByFileName(String name);
    List<FileEntity> findFileEntitiesByIdIsIn(List<Long> fileIds);
}
