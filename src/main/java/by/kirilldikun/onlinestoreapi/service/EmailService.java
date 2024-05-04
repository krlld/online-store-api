package by.kirilldikun.onlinestoreapi.service;

import by.kirilldikun.onlinestoreapi.dto.EmailDto;

public interface EmailService {

    void sendToAllUsers(EmailDto emailDto);
}
