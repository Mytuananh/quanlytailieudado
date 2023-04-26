package com.dado.quanlytailieu.repository;

import com.dado.quanlytailieu.entity.MessageEntity;
import com.dado.quanlytailieu.entity.RoomChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findAllByRoomChat(RoomChatEntity roomChat);

}
