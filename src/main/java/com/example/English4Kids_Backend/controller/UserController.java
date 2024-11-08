package com.example.English4Kids_Backend.controller;


import com.example.English4Kids_Backend.dtos.UserInfo;
import com.example.English4Kids_Backend.dtos.UserSessionDTO;
import com.example.English4Kids_Backend.dtos.process.UserProcessLearning;
import com.example.English4Kids_Backend.entities.UserProgress;
import com.example.English4Kids_Backend.entities.UserSession;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.services.UserService;
import com.example.English4Kids_Backend.services.UserSessionService;
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
    private final UserSessionService userSessionService ;

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

    @GetMapping("/process/{userId}")
    public ResponseEntity<UserProcessLearning> getUserProcessLearning(@PathVariable Integer userId) {
        UserProcessLearning userProcessLearning = userService.getUserProcessLearning(userId);
        return ResponseEntity.ok(userProcessLearning);
    }
    @PostMapping("/process/add-user-process")
    public ResponseEntity<UserSession> saveUserProcess(@RequestBody UserSessionDTO userProgress){
        try {
            return ResponseEntity.ok(userSessionService.createUserSession(userProgress))  ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @PostMapping("/update-user-point")
    public ResponseEntity<UserInfo> updateUserPoint(@RequestBody UserInfo userUpdate){
        try {
            return ResponseEntity.ok(userService.updateUserPoint(userUpdate)) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/get-user-ranking")
    public ResponseEntity<List<UserInfo>> getUserRanking(){
        try{
            return ResponseEntity.ok(userService.getUesrRanking());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

}
