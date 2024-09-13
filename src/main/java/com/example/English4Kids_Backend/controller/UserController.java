package com.example.English4Kids_Backend.controller;


import com.example.English4Kids_Backend.dtos.UserInfo;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1/user")
public class UserController {
    private  final UserService userService ;

    @GetMapping
    public ResponseEntity<UserInfo> getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userService.getCurrentUser(authentication));
    }
}
