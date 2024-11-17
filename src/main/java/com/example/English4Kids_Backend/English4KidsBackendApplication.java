package com.example.English4Kids_Backend;

import com.example.English4Kids_Backend.entities.Lesson;
import com.example.English4Kids_Backend.entities.Role;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.repositories.LessonRepository;
import com.example.English4Kids_Backend.repositories.UserRepository;
import com.example.English4Kids_Backend.repositories.VocabularyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class English4KidsBackendApplication {

	private final LessonRepository lessonRepository;
	private final VocabularyRepository vocabularyRepository;
	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(English4KidsBackendApplication.class, args);

	}
//	@Bean
//	@Transactional
//	public int addVocabularyToLesson() {
//		Lesson lesson = lessonRepository.findById(1L)
//				.orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
//
//		Vocabulary vocabulary = vocabularyRepository.findById(1L)
//				.orElseThrow(() -> new EntityNotFoundException("Vocabulary not found"));
//
//		lesson.getVocabularies().add(vocabulary);
//		vocabulary.getLessons().add(lesson);
//
//		lessonRepository.save(lesson);
//		return 1;
//	}

	@Bean
	public CommandLineRunner commandLineRunner(LessonRepository lessonRepository , VocabularyRepository vocabularyRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {


//			for (int i = 0 ;i<10 ;i++){
//				Vocabulary vocabulary = Vocabulary.builder()
//						.word("test" + i)
//						.meaning("nghĩa của test " + i)
//						.pronunciation("phiên âm của test " + i)
//						.type(VocabularyType.NOUN)
//						.image("https://english-for-kids.s3.ap-southeast-1.amazonaws.com/2ab2687b90ec0af507f862d76b94b08f.jpg")
//						.audio("https://english-for-kids.s3.ap-southeast-1.amazonaws.com/pronunciation_en_skill.mp3")
//						.build();
//				vocabularyRepository.save(vocabulary);
//			}
//			User admin = User.builder()
//					.firstName("admin")
//					.lastName("admin")
//					.email("vohongphuc57371@gmail.com")
//					.password(passwordEncoder.encode("123"))
//					.role(Role.ADMIN)
//					.build();
//			userRepository.save(admin);
////			 tạo một user role user
//
//			User user = User.builder()
//					.firstName("user")
//					.lastName("user")
//					.email("foxfessor@gmail.com")
//					.password(passwordEncoder.encode("123"))
//					.role(Role.USER)
//					.dailyPoints(0)
//					.lastLearningDate(LocalDate.now())
//					.streak(0)
//					.weeklyPoints(0)
//					.totalPoints(0)
//					.build();
//			userRepository.save(user);
//
//			for (int i = 0 ;i<10 ;i++){
//				User user1 = User.builder()
//						.firstName("Hồng")
//						.lastName("Phúc " + i )
//						.email("foxfessor"+ i +"@gmail.com")
//						.password(passwordEncoder.encode("123"))
//						.role(Role.USER)
//						.dailyPoints(0)
//						.lastLearningDate(LocalDate.now())
//						.streak(0)
//						.weeklyPoints(i*100)
//						.totalPoints(0)
//						.build();
//				userRepository.save(user1);
//			}
		};
	}
}
