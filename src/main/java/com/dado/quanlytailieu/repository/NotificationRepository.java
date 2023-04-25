package com.dado.quanlytailieu.repository;

import com.dado.quanlytailieu.entity.NotificationEntity;
import com.dado.quanlytailieu.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    Iterable<NotificationEntity> findAllByUser(UserEntity user);
}
