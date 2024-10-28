package com.example.English4Kids_Backend.controller;

import com.example.English4Kids_Backend.dtos.CreateStudyScheduleRequest;
import com.example.English4Kids_Backend.entities.StudySchedule;
import com.example.English4Kids_Backend.services.StudyScheduleService;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/study-schedule")
@RequiredArgsConstructor
public class StudyScheduleController {

    private final StudyScheduleService studyScheduleService;

    @PostMapping("/create")
    public String createStudySchedule(@RequestBody CreateStudyScheduleRequest request) {
        System.out.println(request.getStartTime());
        StudySchedule studySchedule = studyScheduleService.save(request);
        return "Study schedule created with id: " + studySchedule.getId();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteStudySchedule(@RequestParam long studyScheduleId) {
        studyScheduleService.delete(studyScheduleId);
        return ResponseEntity.ok().build();
    }
}
