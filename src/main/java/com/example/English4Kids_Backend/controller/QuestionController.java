package com.example.English4Kids_Backend.controller;


import com.example.English4Kids_Backend.dtos.lessonDTO.QuestionRequestDTO;
import com.example.English4Kids_Backend.entities.Question;
import com.example.English4Kids_Backend.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    @PostMapping("/create")
    public ResponseEntity<Question> createQuestion(@RequestBody QuestionRequestDTO questionRequestDTO) {
        Question createdQuestion = questionService.createQuestion(questionRequestDTO);
        return ResponseEntity.ok(createdQuestion);
    }
   
}
