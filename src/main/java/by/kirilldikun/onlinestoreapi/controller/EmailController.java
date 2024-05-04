package by.kirilldikun.onlinestoreapi.controller;

import by.kirilldikun.onlinestoreapi.dto.EmailDto;
import by.kirilldikun.onlinestoreapi.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendToAllUsers(@Valid @RequestBody EmailDto emailDto) {
        emailService.sendToAllUsers(emailDto);
    }
}
