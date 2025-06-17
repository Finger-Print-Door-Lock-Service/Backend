package com.physicalcomputing.fingerprintdoorlock.service.mailService;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    public void sendAlertEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to); // 수신자
        message.setSubject(subject); // 제목
        message.setText(text); // 본문
        message.setFrom("qudghk4084@gmail.com"); // 발신자

        mailSender.send(message);
    }
}
