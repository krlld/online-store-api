package by.kirilldikun.onlinestoreapi.controller;

import by.kirilldikun.onlinestoreapi.dto.UserDto;
import by.kirilldikun.onlinestoreapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class UserController {

    public static final String FETCH_USERDATA_MAPPING = "/userdata";

    private final UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(FETCH_USERDATA_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserdata(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        return userService.findByEmail(email);
    }
}
