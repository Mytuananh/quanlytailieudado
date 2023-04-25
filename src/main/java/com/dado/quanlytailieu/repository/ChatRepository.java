package com.dado.quanlytailieu.repository;

import com.dado.quanlytailieu.entity.RoomChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<RoomChatEntity, Long> {
    Optional<RoomChatEntity> findByName(String name);
}
