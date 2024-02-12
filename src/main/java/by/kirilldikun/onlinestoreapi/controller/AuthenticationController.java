package by.kirilldikun.onlinestoreapi.controller;

import by.kirilldikun.onlinestoreapi.dto.AuthenticationRequest;
import by.kirilldikun.onlinestoreapi.dto.AuthenticationResponse;
import by.kirilldikun.onlinestoreapi.dto.RegistrationRequest;
import by.kirilldikun.onlinestoreapi.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return authenticationService.register(registrationRequest);
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticate(authenticationRequest);
    }
}
