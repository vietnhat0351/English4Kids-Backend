package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.Flashcard;
import com.example.English4Kids_Backend.entities.FlashcardSet;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ExtendedFlashcardSetRepository {

    private final FlashcardSetRepository flashcardSetRepository;
    private final JdbcTemplate jdbcTemplate;

    public List<FlashcardSet> findAllUserId(long userId) {
        String query = "SELECT fs.id, fs.created_at, fs.description, fs.name, f.id as flashcard_id, f.audio, f.category, f.description as flashcard_description, f.image, f.meaning, f.word " +
                "FROM flashcard_set fs " +
                "INNER JOIN flashcard_set_user fsu ON fs.id = fsu.flashcard_set_id " +
                "INNER JOIN flashcard f ON fs.id = f.flashcard_set_id " +
                "WHERE fsu.user_id = ?";

        return jdbcTemplate.query(query, new Object[]{userId}, rs -> {
            List<FlashcardSet> flashcardSets = new ArrayList<>();
            FlashcardSet flashcardSet = null;
            List<Flashcard> flashcards = null;
            long currentSetId = -1;

            while (rs.next()) {
                long id = rs.getLong("id");
                if (id != currentSetId) {
                    flashcardSet = new FlashcardSet();
                    flashcardSet.setId(id);
                    flashcardSet.setName(rs.getString("name"));
                    flashcardSet.setDescription(rs.getString("description"));
                    flashcardSet.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    flashcards = new ArrayList<>();
                    flashcardSet.setFlashcards(flashcards);
                    flashcardSets.add(flashcardSet);
                    currentSetId = id;
                }

                Flashcard flashcard = new Flashcard();
                flashcard.setId(rs.getString("flashcard_id"));
                flashcard.setWord(rs.getString("word"));
                flashcard.setMeaning(rs.getString("meaning"));
                flashcard.setImage(rs.getString("image"));
                flashcard.setAudio(rs.getString("audio"));
                flashcard.setCategory(rs.getString("category"));
                flashcard.setDescription(rs.getString("flashcard_description"));
                flashcards.add(flashcard);
            }

            return flashcardSets;
        });
    }
}

