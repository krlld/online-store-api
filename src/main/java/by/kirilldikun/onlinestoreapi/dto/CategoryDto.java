package by.kirilldikun.onlinestoreapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryDto {

    private Long id;

    @NotBlank
    private String name;
}
