package com.example.English4Kids_Backend.controller;

import com.example.English4Kids_Backend.dtos.UserInfo;
import com.example.English4Kids_Backend.dtos.lessonDTO.LessonCompletionDTO;
import com.example.English4Kids_Backend.dtos.lessonDTO.LessonDTO;
import com.example.English4Kids_Backend.dtos.lessonDTO.UserLessonRequestDTO;
import com.example.English4Kids_Backend.entities.Lesson;
import com.example.English4Kids_Backend.services.LessonService;
import com.example.English4Kids_Backend.services.UserLessonService;
import com.example.English4Kids_Backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final UserService userService;
    private final UserLessonService userLessonService;

    @GetMapping("/get-all")
    public ResponseEntity<List<LessonDTO>> getAllLessons() {
        List<LessonDTO> lessons = lessonService.getAllLessons();
        return ResponseEntity.ok(lessons);
    }
    @GetMapping("/get-all-for-user")
    public ResponseEntity<List<LessonCompletionDTO>> getAllLessonsUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo user = userService.getCurrentUser(authentication);

        List<LessonCompletionDTO> lessons = lessonService.getLessonsWithCompletionStatus(user.getId());
        return ResponseEntity.ok(lessons);
    }
    @PostMapping("/add-user-lesson")
    public ResponseEntity<?> addUserLesson(@RequestBody UserLessonRequestDTO dto) {
        try {
            userLessonService.addOrUpdateUserLesson(dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("UserLesson processed successfully");
    }


    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable long id) {
        LessonDTO lesson = lessonService.getLessonById(id);
        return ResponseEntity.ok(lesson);
    }
    @GetMapping("/get-lesson/{id}")
    public ResponseEntity<LessonCompletionDTO> getLessonByIdUser(@PathVariable long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo user = userService.getCurrentUser(authentication);

        LessonCompletionDTO lesson = lessonService.getLessonByIdUser( id,user.getId());
        return ResponseEntity.ok(lesson);
    }
    @PostMapping("/create")
    public ResponseEntity<LessonDTO> createLesson(@RequestBody LessonDTO lessonDTO) {
        LessonDTO createdLesson = lessonService.createLesson(lessonDTO);
        return ResponseEntity.ok(createdLesson);
    }
    @PostMapping("/update")
    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson) {
        Lesson createdLesson = lessonService.updateLesson(lesson);
        return ResponseEntity.ok(createdLesson);
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.ok("Lesson deleted successfully");
    }
    @GetMapping("/get-point-seven-day/{userId}")
    public ResponseEntity<?> getPointSevenDay(@PathVariable int userId) {
        return ResponseEntity.ok(lessonService.getPointSevenDay(userId));
    }
    @GetMapping("/get-statistic")
    public ResponseEntity<?> getPointSevenDay2(  @RequestParam long lessonId, @RequestParam long userId) {
        return ResponseEntity.ok(lessonService.getStatistic(lessonId, userId));
    }


}
