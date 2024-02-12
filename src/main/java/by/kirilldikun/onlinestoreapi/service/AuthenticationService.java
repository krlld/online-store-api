package by.kirilldikun.onlinestoreapi.service;

import by.kirilldikun.onlinestoreapi.dto.AuthenticationRequest;
import by.kirilldikun.onlinestoreapi.dto.AuthenticationResponse;
import by.kirilldikun.onlinestoreapi.dto.RegistrationRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegistrationRequest registrationRequest);

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
