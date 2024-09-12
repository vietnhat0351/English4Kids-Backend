package com.example.English4Kids_Backend.controller;


import com.example.English4Kids_Backend.entities.OPT;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final Map<String, OPT> otpMap = new HashMap<>();


    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    private final JavaMailSender javaMailSender;

    @GetMapping("/test")
    public String test() throws MessagingException {
        String to = "vietnhat0351@gmail.com";
        SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("your-email@gmail.com");
        message.setTo(to);
        message.setSubject("subject");
        OPT otp = generateOtp();
        message.setText(otp.getOtp());

        otpMap.put(to, otp);


        javaMailSender.send(message);
        System.out.println("Mail sent successfully...");
        return "Mail sent successfully...";
    }

    @GetMapping("/validate")
    public String validate(@RequestParam String email, @RequestParam String otp) {
        if (otpMap.containsKey(email) && otpMap.get(email).getOtp().equals(otp) && otpMap.get(email).getExpiryDate().isAfter(LocalDateTime.now())) {
            otpMap.remove(email);
            return "Valid";
        }
        return "Invalid";
    }

    private static final String CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    public OPT generateOtp() {
        // Lấy giờ hiện tại
        LocalDateTime now = LocalDateTime.now();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return new OPT(otp.toString(), now.plusMinutes(2));
    }
}
