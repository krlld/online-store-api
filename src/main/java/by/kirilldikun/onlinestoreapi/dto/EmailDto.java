package by.kirilldikun.onlinestoreapi.dto;

import jakarta.validation.constraints.NotBlank;

public record EmailDto(
        @NotBlank String subject,
        @NotBlank String text) {

}
