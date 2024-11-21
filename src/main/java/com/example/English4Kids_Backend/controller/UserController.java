package com.example.English4Kids_Backend.controller;


import com.example.English4Kids_Backend.dtos.UserInfo;
import com.example.English4Kids_Backend.dtos.lessonDTO.VocabularyDTO;
import com.example.English4Kids_Backend.dtos.userDTO.UserVocabularyRequest;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.services.UserService;
import com.example.English4Kids_Backend.services.UserVocabularyService;
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
    private final UserVocabularyService userVocabularyService;

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
    public ResponseEntity<List<VocabularyDTO>> getAllVocabularies() {
        List<VocabularyDTO> vocabularies = vocabularyService.getAllVocabularies();
        return ResponseEntity.ok(vocabularies);
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
    @PostMapping("/update-user-info")
    public ResponseEntity<UserInfo> updateUserInfo(@RequestBody UserInfo userUpdate){
        try {
            return ResponseEntity.ok(userService.updateUserInfo(userUpdate)) ;
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
    @PostMapping("/update-user-vocabulary")
    public ResponseEntity<?> studyVocabulary(@RequestBody UserVocabularyRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo user = userService.getCurrentUser(authentication);

        userVocabularyService.userStudiedVocabulary(Math.toIntExact(user.getId()), request.getVocabularyId());

        return ResponseEntity.ok("Vocabulary study record updated successfully.");
    }

    @GetMapping("/voca-count")
    public ResponseEntity<Long> getVocabularyCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo user = userService.getCurrentUser(authentication);

        Long count = userVocabularyService.countVocabulariesByUserId(Math.toIntExact(user.getId()));
        return ResponseEntity.ok(count);
    }

}
