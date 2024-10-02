package com.example.English4Kids_Backend.controller;

import com.example.English4Kids_Backend.dtos.pixabayresponse.PixabayResponse;
import com.example.English4Kids_Backend.services.PixabayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pixabay")
public class PixabayController {
    private final PixabayService pixabayService;

    // Endpoint nhận GET request và trả về danh sách các hình ảnh từ Pixabay
    @GetMapping("/search")
    public ResponseEntity<PixabayResponse> searchImages(@RequestParam String query) {
        PixabayResponse response = pixabayService.searchImages(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
