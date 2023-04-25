package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.entity.NotificationEntity;
import com.dado.quanlytailieu.entity.UserEntity;
import com.dado.quanlytailieu.service.NotificationService;
import com.dado.quanlytailieu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/notifications")
public class NotificationRestController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<NotificationEntity>> findAllByUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        UserEntity currentUser = userService.findByUsername(username);
        return new ResponseEntity<>(notificationService.findAllByUser(currentUser), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        notificationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
