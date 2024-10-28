package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.dtos.CreateStudyScheduleRequest;
import com.example.English4Kids_Backend.entities.StudySchedule;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.repositories.StudyScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudyScheduleService {

    private final StudyScheduleRepository studyScheduleRepository;

    private final JavaMailSender javaMailSender;

    @Scheduled(fixedRate = 10000)
    public void scheduleFixedRateTask() {
        // Gửi email nhắc nhở người dùng tham gia lịch học
        log.info("Sending email to users for study schedule");
        // lấy ra tất cả các lịch học có startTime < now
        List<StudySchedule> studySchedules = studyScheduleRepository.findAllByStartTimeBefore(LocalDateTime.now());
        studySchedules.forEach(studySchedule -> {
            studySchedule.getUsers().forEach(user -> {
                // Gửi email
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.getEmail());
                message.setSubject("Hey " + user.getFirstName() + "! Đã đến giờ học rồi nè!");
                message.setText("Hello " + user.getFirstName() + "!\n\n" +
                        "Đã đến lúc chúng ta cùng học tiếng Anh rồi! Đừng để kiến thức chạy trốn nhé, nhanh chóng hoàn thành bài học nào! 😄\n\n" +
                        "Mỗi ngày một chút, bạn sẽ giỏi lên nhanh thôi. Nhớ rằng chúng tôi luôn bên cạnh cổ vũ bạn.\n\n" +
                        "Chúc bạn học thật vui và hiệu quả!");
                javaMailSender.send(message);
                log.info("Email sent to user: " + user.getEmail());
            });
            studySchedule.setStartTime(studySchedule.getStartTime().plusDays(1));
            studyScheduleRepository.save(studySchedule);
        }
        );
    }

    public StudySchedule save(CreateStudyScheduleRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // set timezone việt nam
        LocalDateTime startTime = request.getStartTime().plusHours(7);

        // kiểm tra xem nếu startTime < now thì set startTime + 1 ngày
        if (startTime.isBefore(LocalDateTime.now())) {
            startTime = startTime.plusDays(1);
        }

        List<StudySchedule> allStudySchedules = studyScheduleRepository.findAll();

        StudySchedule studySchedule = allStudySchedules.stream()
                .filter(s -> s.getUsers().contains(user))
                .findFirst()
                .orElse(null);

        if (studySchedule != null) {
            studySchedule.getUsers().remove(user);
            studyScheduleRepository.save(studySchedule);
        }

        studySchedule = studyScheduleRepository.findFirstByStartTime(startTime).orElse(null);

        if (studySchedule == null) {
            studySchedule = new StudySchedule();

            studySchedule.setStartTime(startTime);

//            studySchedule.setUsers(Collections.singletonList(user));
            studySchedule.setUsers(List.of(user));
        } else {
            studySchedule.getUsers().add(user);
        }
        return studyScheduleRepository.save(studySchedule);
    }

    public void delete(long studyScheduleId) {
        StudySchedule studySchedule = studyScheduleRepository.findById(studyScheduleId).orElse(null);
        if (studySchedule == null) {
            throw new RuntimeException("Study schedule not found");
        }

        if(studySchedule.getUsers().isEmpty()) {
            studyScheduleRepository.delete(studySchedule);
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        studySchedule.getUsers().remove(user);
        studyScheduleRepository.save(studySchedule);
    }

}
