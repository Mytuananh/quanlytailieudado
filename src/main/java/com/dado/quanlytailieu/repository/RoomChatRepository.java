package com.dado.quanlytailieu.repository;

import com.dado.quanlytailieu.entity.RoomChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomChatRepository extends JpaRepository<RoomChatEntity, Long> {


}
