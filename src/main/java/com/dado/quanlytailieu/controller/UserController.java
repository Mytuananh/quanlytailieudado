package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.dto.UserInfoDTO;
import com.dado.quanlytailieu.entity.MessageEntity;
import com.dado.quanlytailieu.service.JwtService;
import com.dado.quanlytailieu.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    private final JwtService jwtService;

    @PostMapping("")
    public UserInfoDTO getUserByUserName(@RequestBody String name) {
        // TODO: remove senderId and using JWT to get senderId
        return userService.getUserByUserName(name);
    }

    @GetMapping("/present")
    public UserInfoDTO getPresentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwt = (String) authentication.getCredentials();
        String userEmail = jwtService.extractUsername(jwt);

        return userService.getUserByEmail(userEmail);

    }
}
