package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.dao.CreateRoomChatForm;
import com.dado.quanlytailieu.entity.RoomChatEntity;
import com.dado.quanlytailieu.entity.UserEntity;
import com.dado.quanlytailieu.service.JwtService;
import com.dado.quanlytailieu.service.RoomChatService;
import com.dado.quanlytailieu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/rooms")
public class RoomChatRestController {
    @Autowired
    private RoomChatService roomChatService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<RoomChatEntity> save(@RequestBody CreateRoomChatForm createRoomChatForm){

        RoomChatEntity roomChat = new RoomChatEntity();
        roomChat.setMessages(new ArrayList<>());
        roomChat.setName(createRoomChatForm.getName());
        roomChat.setUserEntityList(userService.getAllUserListByListId(createRoomChatForm.getUserEntityList()));
        return new ResponseEntity<>(roomChatService.save(roomChat), HttpStatus.CREATED);
    }
    @GetMapping("/{roomChatId}")
    public ResponseEntity<RoomChatEntity> findRoomChatByRoomChatId(@PathVariable Long roomChatId){
        Optional<RoomChatEntity> roomChat = roomChatService.findByRoomChatId(roomChatId);
        return roomChat.map(roomChatEntity -> new ResponseEntity<>(roomChatEntity, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/userId")
    public ResponseEntity<Iterable<RoomChatEntity>> findAllByUserId(@RequestBody Long userId){
        return new ResponseEntity<>(roomChatService.findAllByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/presentUser")
    public ResponseEntity<Iterable<RoomChatEntity>> findAllByPresentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = ((UserEntity) authentication.getPrincipal()).getEmail() ;
        return new ResponseEntity<>(userService.findAllRoomChatByPresentUser(userEmail), HttpStatus.OK);
    }
}
