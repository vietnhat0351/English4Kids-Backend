package com.example.English4Kids_Backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Integer dailyPoints;
    private Integer weeklyPoints;
    private Integer totalPoints;
    private Integer streak; // Continuous streak
    private LocalDate lastLearningDate;
    private String gender;
    private LocalDate dob;
    private LocalDate joinDate;
    private Integer wordshakeHighScore;
    private LocalDateTime lastWordshakeDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserLesson> userLessons = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
