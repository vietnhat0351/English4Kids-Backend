package com.example.English4Kids_Backend.controller;

import com.example.English4Kids_Backend.services.TextToSpeechService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tts")
public class TextToSpeechController {
    private final TextToSpeechService textToSpeechService;

    // Endpoint nhận POST request và trả về file âm thanh dạng byte[]
    @PostMapping("/synthesize")
    public ResponseEntity<byte[]> synthesizeText(@RequestBody Map<String, String> request) {
        String text = request.get("text");

        if (text != null && !text.isEmpty()) {
            byte[] audioData = textToSpeechService.synthesizeTextToBytes(text);

            // Cấu hình header để trả về file âm thanh trực tiếp
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "audio/mpeg");

            return new ResponseEntity<>(audioData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
