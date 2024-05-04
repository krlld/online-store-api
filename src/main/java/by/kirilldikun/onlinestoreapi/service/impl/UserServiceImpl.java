package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.UserDto;
import by.kirilldikun.onlinestoreapi.entity.User;
import by.kirilldikun.onlinestoreapi.exceptions.NotFoundException;
import by.kirilldikun.onlinestoreapi.mapper.UserMapper;
import by.kirilldikun.onlinestoreapi.repository.UserRepository;
import by.kirilldikun.onlinestoreapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDto)
                .orElseThrow(() -> new NotFoundException("User with id: %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toUserDto)
                .orElseThrow(() -> new NotFoundException("User with email: %S not found".formatted(email)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllEmails() {
        return userRepository.findAll()
                .stream()
                .map(User::getEmail)
                .toList();
    }
}
