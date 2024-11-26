package com.example.English4Kids_Backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flashcard_set")
public class FlashcardSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "flashcard_set_id")
    private List<CardMatchingRecord> cardMatchingRecords;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "flashcard_set_id")
    private List<Flashcard> flashcards;
    @ManyToMany
    @JoinTable(
            name = "flashcard_set_user",
            joinColumns = @JoinColumn(name = "flashcard_set_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;
}
