package com.example.English4Kids_Backend;

import com.example.English4Kids_Backend.entities.Role;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.ResultSet;

@SpringBootApplication
@EnableScheduling
public class English4KidsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(English4KidsBackendApplication.class, args);
	}

//	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// tạo một user role admin
			User admin = User.builder()
					.firstName("admin")
					.lastName("admin")
					.email("vohongphuc57371@gmail.com")
					.password(passwordEncoder.encode("123"))
					.role(Role.ADMIN)
					.build();
			userRepository.save(admin);
		};
	}

}
