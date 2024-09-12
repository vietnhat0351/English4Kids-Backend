package com.example.English4Kids_Backend.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenResponse {
    private String accessToken;
    private String refreshToken;
}
