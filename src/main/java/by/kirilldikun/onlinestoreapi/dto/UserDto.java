package by.kirilldikun.onlinestoreapi.dto;

import java.util.List;

public record UserDto(
        Long id,
        String email,
        String name,
        List<String> roles
) {

}
