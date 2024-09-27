package com.example.English4Kids_Backend.controller;


import com.example.English4Kids_Backend.dtos.UserInfo;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.services.UserService;
import com.example.English4Kids_Backend.services.VocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService ;
    private final VocabularyService vocabularyService;

//    @GetMapping("/current")
    @GetMapping("/current")
    public ResponseEntity<UserInfo> getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userService.getCurrentUser(authentication));
    }
    @GetMapping("/test")
    public String hello(){
        return "Hello World";
    }
    @GetMapping("/vocabularies")
    public ResponseEntity<List<Vocabulary>> getVocabularies(){
        return ResponseEntity.ok(vocabularyService.getAllVocabularies());
    }

}
