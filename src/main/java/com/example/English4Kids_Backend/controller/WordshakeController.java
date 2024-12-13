package com.example.English4Kids_Backend.controller;

import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.entities.WordshakeHighScore;
import com.example.English4Kids_Backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/wordshake")
@RequiredArgsConstructor
public class WordshakeController {

    private final UserRepository userRepository;


    @PostMapping("/update-high-score")
    public ResponseEntity<String> updateHighScore(@RequestParam Integer highScore) {
        // Cập nhật điểm cao nhất của người chơi trong game Wordshake

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(user.getEmail()).get();
        currentUser.setWordshakeHighScore(highScore);
        currentUser.setLastWordshakeDate(LocalDateTime.now());
        userRepository.save(currentUser);
        return ResponseEntity.ok("Update high score successfully");
    }

    @GetMapping("/get-high-score")
    public Map<String, Object> getHighScore() {
        // Lấy điểm cao nhất của người chơi trong game Wordshake

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(user.getEmail()).get();
        return Map.of(
                "highScore", currentUser.getWordshakeHighScore() == null ? 0 : currentUser.getWordshakeHighScore(),
                "lastWordshakeDate", currentUser.getLastWordshakeDate() == null ? 0 : currentUser.getLastWordshakeDate()
        );
    }
}
