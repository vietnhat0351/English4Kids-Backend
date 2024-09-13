package com.example.English4Kids_Backend.dtos;

import com.example.English4Kids_Backend.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
