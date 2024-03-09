package by.kirilldikun.onlinestoreapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.time.ZonedDateTime;

public record ReviewDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) Long id,
        @NotBlank String content,
        @Range(min = 0, max = 5) Integer rating,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) ZonedDateTime createdAt,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @NotNull Long userId,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) String userName,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @NotNull Long productId,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) ProductDto product
) {

}
