package by.kirilldikun.onlinestoreapi.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(

        @NotBlank String email,

        @NotBlank String password
) {

}
