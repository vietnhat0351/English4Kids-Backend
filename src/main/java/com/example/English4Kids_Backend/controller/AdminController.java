package com.example.English4Kids_Backend.controller;

import com.example.English4Kids_Backend.dtos.AuthResponse;
import com.example.English4Kids_Backend.dtos.RegisterRequest;
import com.example.English4Kids_Backend.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AuthenticationService authenticationService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public String get(){
        return "GET:: ADMIN";
    }
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public String post(){
        return "POST:: ADMIN";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public String put(){
        return "PUT:: ADMIN";
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public String delete(){
        return "DELETE:: ADMIN";
    }
    @PostMapping("/register")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.registerForAdmin(request));
    }
}
