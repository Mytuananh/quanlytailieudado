package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.entity.NotificationEntity;
import com.dado.quanlytailieu.entity.UserEntity;
import com.dado.quanlytailieu.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService{
    @Autowired
    private NotificationRepository notificationRepository;
    public Iterable<NotificationEntity> findAll() {
        return notificationRepository.findAll();
    }

    public Optional<NotificationEntity> findById(Long id) {
        return notificationRepository.findById(id);
    }

    public NotificationEntity save(NotificationEntity notification) {
        return notificationRepository.save(notification);
    }

    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }

    public Iterable<NotificationEntity> findAllByUser(UserEntity user) {
        return notificationRepository.findAllByUser(user);
    }
}
