package by.kirilldikun.onlinestoreapi.service;

import by.kirilldikun.onlinestoreapi.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto findById(Long id);

    UserDto findByEmail(String email);

    List<String> findAllEmails();
}
