package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.entities.Otp;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final Map<String, Otp> otpMap = new HashMap<>();
    private final JavaMailSender javaMailSender;
    private static final String CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public String sendEmail(String email) throws MessagingException {
//        String to = "vietnhat0351@gmail.com";
        email = email.trim();
        if (!isValidEmail(email)) {
            return "Invalid email";
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("subject");
            Otp otp = generateOtp();
            message.setText(otp.getOtp());
            otpMap.put(email, otp);
            javaMailSender.send(message);
            System.out.println("Mail sent successfully...");
            return "Mail sent successfully...";
        } catch (MailException e) {
            System.err.println("Error sending email: " + e.getMessage());
            throw new MessagingException("Failed to send email", e);
        }
    }
    public Boolean validate( String email, String otp) {
        if (otpMap.containsKey(email) && otpMap.get(email).getOtp().equals(otp) && otpMap.get(email).getExpiryDate().isAfter(LocalDateTime.now())) {
            otpMap.remove(email);
            return true;
        }
        return false;
    }
    public Otp generateOtp() {
        // Lấy giờ hiện tại
        LocalDateTime now = LocalDateTime.now();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return new Otp(otp.toString(), now.plusMinutes(2));
    }
}
