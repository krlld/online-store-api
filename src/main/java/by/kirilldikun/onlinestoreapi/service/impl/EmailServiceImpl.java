package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.EmailDto;
import by.kirilldikun.onlinestoreapi.service.EmailService;
import by.kirilldikun.onlinestoreapi.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private final String email;

    private final JavaMailSender javaMailSender;

    private final UserService userService;

    public EmailServiceImpl(
            @Value("spring.mail.username") String email,
            JavaMailSender javaMailSender,
            UserService userService) {
        this.email = email;
        this.javaMailSender = javaMailSender;
        this.userService = userService;
    }

    @Override
    public void sendToAllUsers(EmailDto emailDto) {
        List<String> emails = userService.findAllEmails();
        List<SimpleMailMessage> messages = emails
                .stream()
                .map(e -> createMessage(e, emailDto))
                .toList();
        javaMailSender.send(messages.toArray(new SimpleMailMessage[0]));
    }

    private SimpleMailMessage createMessage(String to, EmailDto emailDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(email);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(emailDto.subject());
        simpleMailMessage.setText(emailDto.text());
        return simpleMailMessage;
    }
}
