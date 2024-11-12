package com.example.English4Kids_Backend;

import com.example.English4Kids_Backend.entities.Role;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
@EnableScheduling
public class English4KidsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(English4KidsBackendApplication.class, args);
	}

//	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			User admin = User.builder()
					.firstName("admin")
					.lastName("admin")
					.email("vohongphuc57371@gmail.com")
					.password(passwordEncoder.encode("123"))
					.role(Role.ADMIN)
					.build();
			userRepository.save(admin);
////			 tạo một user role user

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

//			for (int i = 0 ;i<10 ;i++){
//				User user = User.builder()
//						.firstName("test")
//						.lastName("user " + i )
//						.email("foxfessor"+ i +"@gmail.com")
//						.password(passwordEncoder.encode("123"))
//						.role(Role.USER)
//						.dailyPoints(0)
//						.lastLearningDate(LocalDate.now())
//						.streak(0)
//						.weeklyPoints(0)
//						.totalPoints(0)
//						.build();
//				userRepository.save(user);
//			}
		};
	}
}
