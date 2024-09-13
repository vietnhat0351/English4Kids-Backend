package com.example.English4Kids_Backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Otp {
    private String otp;
    private LocalDateTime expiryDate;
}
