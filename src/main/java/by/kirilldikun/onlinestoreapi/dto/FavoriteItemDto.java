package by.kirilldikun.onlinestoreapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record FavoriteItemDto(
        @NotNull Long userId,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @NotNull Long productId,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) ProductDto productDto) {

}
