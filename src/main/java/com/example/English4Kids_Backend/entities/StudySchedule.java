package com.example.English4Kids_Backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "study_schedule")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private LocalDateTime startTime;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "study_schedule_id")
    private List<User> users;
}
