package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.configuration.UserDetailsImpl;
import by.kirilldikun.onlinestoreapi.dto.AuthenticationRequest;
import by.kirilldikun.onlinestoreapi.dto.AuthenticationResponse;
import by.kirilldikun.onlinestoreapi.dto.RegistrationRequest;
import by.kirilldikun.onlinestoreapi.entity.Role;
import by.kirilldikun.onlinestoreapi.entity.User;
import by.kirilldikun.onlinestoreapi.exceptions.AlreadyExistsException;
import by.kirilldikun.onlinestoreapi.repository.UserRepository;
import by.kirilldikun.onlinestoreapi.service.AuthenticationService;
import by.kirilldikun.onlinestoreapi.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        String email = registrationRequest.email();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new AlreadyExistsException("User with email: %s already exists".formatted(email));
        }
        User user = new User()
                .setEmail(email)
                .setPassword(passwordEncoder.encode(registrationRequest.password()))
                .setName(registrationRequest.name())
                .setRoles(Set.of(new Role().setName("ROLE_USER")));
        userRepository.save(user);
        String token = jwtService.generateToken(new UserDetailsImpl(user));
        return new AuthenticationResponse(token);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.email(),
                        authenticationRequest.password()
                )
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.email());
        String token = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(token);
    }
}
