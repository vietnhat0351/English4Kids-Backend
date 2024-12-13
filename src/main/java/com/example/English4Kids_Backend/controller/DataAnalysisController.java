package com.example.English4Kids_Backend.controller;

import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/data-analysis")
@RequiredArgsConstructor
public class DataAnalysisController {

    private final UserRepository userRepository;

    @GetMapping("/get-data")
    public Map<String, Object> getGenderAnalysis() {
        // thóng kê giới tính của người dùng
        Map<String, Long> genderAnalysis;
        List<User> users = userRepository.findAll();

        long maleCount = users.stream().filter(user -> user.getGender() != null && user.getGender().equals("male")).count();
        long femaleCount = users.stream().filter(user -> user.getGender() != null && user.getGender().equals("female")).count();
        long unknownCount = users.stream().filter(user -> user.getGender() == null).count();

        genderAnalysis = Map.of("male", maleCount, "female", femaleCount, "unknown", unknownCount);

        // Số tài khoản đã tạo trong 30 ngày gần đây
        // key: ngày, value: số tài khoản được tạo, format: DD/MM
        Map<String, Long> accountCreatedInLast30Days = new HashMap<>();

        users.stream()
                .filter(user -> user.getJoinDate() != null && user.getJoinDate().isAfter(LocalDate.now().minusDays(30)))
                .forEach(user -> {
                    String joinMonth = user.getJoinDate().getMonthValue() < 10 ? "0" + user.getJoinDate().getMonthValue() : String.valueOf(user.getJoinDate().getMonthValue());
                    String joinDay = user.getJoinDate().getDayOfMonth() < 10 ? "0" + user.getJoinDate().getDayOfMonth() : String.valueOf(user.getJoinDate().getDayOfMonth());
                    String joinDate = joinDay + "/" + joinMonth;
                    accountCreatedInLast30Days.put(joinDate, accountCreatedInLast30Days.getOrDefault(joinDate, 0L) + 1);
                });

        return new HashMap<>() {{
            put("genderAnalysis", genderAnalysis);
            put("accountCreatedInLast30Days", accountCreatedInLast30Days);
        }};
    }
}
