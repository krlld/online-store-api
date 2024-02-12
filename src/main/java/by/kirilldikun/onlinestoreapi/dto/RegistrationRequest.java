package by.kirilldikun.onlinestoreapi.dto;

import jakarta.validation.constraints.NotBlank;

public record RegistrationRequest(

        @NotBlank String email,

        @NotBlank String password,

        @NotBlank String name
) {

}
