package by.kirilldikun.onlinestoreapi.service;

import by.kirilldikun.onlinestoreapi.dto.UserDto;

public interface UserService {

    UserDto findById(Long id);

    UserDto findByEmail(String email);
}
