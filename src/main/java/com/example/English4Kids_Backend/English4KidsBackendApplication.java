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

    @Bean
    public CommandLineRunner commandLineRunner(LessonRepository lessonRepository, VocabularyRepository vocabularyRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (args[0] != null && args[0].equals("init")) {
                User admin = User.builder()
                        .firstName("Hồng")
                        .lastName("Phúc")
                        .email("vohongphuc57371@gmail.com")
                        .password(passwordEncoder.encode("123"))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(admin);
//			 tạo một user role user

                User user = User.builder()
                        .firstName("Đặng")
                        .lastName("Mai Hương")
                        .email("foxfessor@gmail.com")
                        .password(passwordEncoder.encode("123"))
                        .avatar("https://assets.quizlet.com/static/i/animals/108.3b3090077134db3.jpg")
                        .role(Role.USER)
                        .dailyPoints(0)
                        .lastLearningDate(LocalDate.now())
                        .streak(0)
                        .weeklyPoints(0)
                        .totalPoints(0)
                        .joinDate(LocalDate.now().minusDays(1))
                        .gender("female")
                        .build();
                userRepository.save(user);


                User user1 = User.builder()
                        .firstName("Võ")
                        .lastName("Thanh Tịnh")
                        .email("foxfessor1" + "@gmail.com")
                        .password(passwordEncoder.encode("123"))
                        .avatar("https://assets.quizlet.com/static/i/animals/108.3b3090077134db3.jpg")
                        .role(Role.USER)
                        .dailyPoints(0)
                        .lastLearningDate(LocalDate.now())
                        .streak(0)
                        .weeklyPoints(100)
                        .totalPoints(0)
                        .joinDate(LocalDate.now().minusDays(2))
                        .gender("male")
                        .build();
                userRepository.save(user1);

                User user2 = User.builder()
                        .firstName("Phạm")
                        .lastName("Hùng Cường")
                        .email("hungcuong" + "@gmail.com")
                        .password(passwordEncoder.encode("123"))
                        .role(Role.USER)
                        .avatar("https://assets.quizlet.com/static/i/animals/108.3b3090077134db3.jpg")
                        .dailyPoints(0)
                        .lastLearningDate(LocalDate.now())
                        .streak(0)
                        .weeklyPoints(200)
                        .totalPoints(200)
                        .joinDate(LocalDate.now().minusDays(1))
                        .gender("male")
                        .build();
                userRepository.save(user2);

                User user3 = User.builder()
                        .firstName("Ngô Trần")
                        .lastName("Yến Nhi")
                        .email("yennhi" + "@gmail.com")
                        .password(passwordEncoder.encode("123"))
                        .role(Role.USER)
                        .dailyPoints(0)
                        .avatar("https://assets.quizlet.com/static/i/animals/108.3b3090077134db3.jpg")
                        .lastLearningDate(LocalDate.now())
                        .streak(0)
                        .weeklyPoints(300)
                        .totalPoints(300)
                        .joinDate(LocalDate.now().minusDays(1))
                        .gender("female")
                        .build();
                userRepository.save(user3);

                User user4 = User.builder()
                        .firstName("Đàm")
                        .lastName("Huy Hoàng")
                        .email("huyhoang" + "@gmail.com")
                        .password(passwordEncoder.encode("123"))
                        .role(Role.USER)
                        .dailyPoints(0)
                        .avatar("https://assets.quizlet.com/static/i/animals/108.3b3090077134db3.jpg")
                        .lastLearningDate(LocalDate.now())
                        .streak(0)
                        .weeklyPoints(400)
                        .gender("male")
                        .joinDate(LocalDate.now().minusDays(3))
                        .totalPoints(400)
                        .build();
                userRepository.save(user4);

                User user5 = User.builder()
                        .firstName("Nguyễn")
                        .lastName("Thu Trâm")
                        .email("chautam" + "@gmail.com")
                        .password(passwordEncoder.encode("123"))
                        .role(Role.USER)
                        .dailyPoints(0)
                        .avatar("https://assets.quizlet.com/static/i/animals/108.3b3090077134db3.jpg")
                        .lastLearningDate(LocalDate.now())
                        .streak(0)
                        .weeklyPoints(50)
                        .joinDate(LocalDate.now().minusDays(1))
                        .totalPoints(500)
                        .gender("female")
                        .build();
                userRepository.save(user5);

                User user6 = User.builder()
                        .firstName("Lê Dương")
                        .lastName("Phương Thảo")
                        .email("phuongthao" + "@gmail.com")
                        .password(passwordEncoder.encode("123"))
                        .role(Role.USER)
                        .dailyPoints(0)
                        .avatar("https://assets.quizlet.com/static/i/animals/108.3b3090077134db3.jpg")
                        .lastLearningDate(LocalDate.now())
                        .streak(0)
                        .weeklyPoints(60)
                        .joinDate(LocalDate.now().minusDays(1))
                        .totalPoints(600)
                        .gender("female")
                        .build();
                userRepository.save(user6);

                User user7 = User.builder()
                        .firstName("Phan")
                        .lastName("Vĩnh Phú")
                        .email("vinhphu" + "@gmail.com")
                        .password(passwordEncoder.encode("123"))
                        .role(Role.USER)
                        .dailyPoints(0)
                        .lastLearningDate(LocalDate.now())
                        .avatar("https://assets.quizlet.com/static/i/animals/108.3b3090077134db3.jpg")
                        .streak(0)
                        .weeklyPoints(70)
                        .joinDate(LocalDate.now().minusDays(5))
                        .totalPoints(700)
                        .build();
                userRepository.save(user7);

                User user8 = User.builder()
                        .firstName("Trịnh")
                        .lastName("Tấn Thành")
                        .email("tanthanh" + "@gmail.com")
                        .password(passwordEncoder.encode("123"))
                        .role(Role.USER)
                        .dailyPoints(0)
                        .lastLearningDate(LocalDate.now())
                        .streak(0)
                        .weeklyPoints(80)
                        .avatar("https://assets.quizlet.com/static/i/animals/108.3b3090077134db3.jpg")
                        .joinDate(LocalDate.now().minusDays(4))
                        .totalPoints(800)
                        .build();
                userRepository.save(user8);

                User user9 = User.builder()
                        .firstName("Phan")
                        .lastName("Thị Hà Trúc")
                        .email("hatruc" + "@gmail.com")
                        .password(passwordEncoder.encode("123"))
                        .role(Role.USER)
                        .dailyPoints(0)
                        .avatar("https://assets.quizlet.com/static/i/animals/108.3b3090077134db3.jpg")
                        .lastLearningDate(LocalDate.now())
                        .streak(0)
                        .weeklyPoints(90)
                        .joinDate(LocalDate.now().minusDays(3))
                        .totalPoints(900)
                        .build();
                userRepository.save(user9);

                User user10 = User.builder()
                        .firstName("Nguyễn")
                        .lastName("Huỳnh Giao")
                        .email("huynhgiao" + "@gmail.com")
                        .password(passwordEncoder.encode("123"))
                        .role(Role.USER)
                        .dailyPoints(0)
                        .avatar("https://assets.quizlet.com/static/i/animals/108.3b3090077134db3.jpg")
                        .lastLearningDate(LocalDate.now())
                        .streak(0)
                        .weeklyPoints(100)
                        .joinDate(LocalDate.now().minusDays(2))
                        .totalPoints(1000)
                        .build();
                userRepository.save(user10);
            }
        };
    }
}
