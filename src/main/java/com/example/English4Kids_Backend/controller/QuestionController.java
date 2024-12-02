package com.example.English4Kids_Backend.controller;


import com.example.English4Kids_Backend.dtos.lessonDTO.QuestionDTO;
import com.example.English4Kids_Backend.dtos.lessonDTO.QuestionDeleteInput;
import com.example.English4Kids_Backend.dtos.lessonDTO.QuestionRequestAddDTO;
import com.example.English4Kids_Backend.dtos.lessonDTO.QuestionRequestDTO;
import com.example.English4Kids_Backend.entities.Question;
import com.example.English4Kids_Backend.repositories.QuestionRepository;
import com.example.English4Kids_Backend.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;

    @PostMapping("/create")
    public ResponseEntity<Question> createQuestion(@RequestBody QuestionRequestDTO questionRequestDTO) {
        Question createdQuestion = questionService.createQuestion(questionRequestDTO);
        return ResponseEntity.ok(createdQuestion);
    }
    @PostMapping("/delete")
    public ResponseEntity<?> deleteQuestion(@RequestBody QuestionDeleteInput questionDeleteInput) {
        questionService.deleteQuestion(questionDeleteInput);
        return ResponseEntity.ok("Question deleted successfully");
    }
    @PostMapping("/update")
    public ResponseEntity<QuestionDTO> updateQuestion(@RequestBody QuestionRequestAddDTO questionRequestDTO) {
        QuestionDTO question = questionService.updateQuestion(questionRequestDTO);
        return ResponseEntity.ok(question);
    }
   
}
