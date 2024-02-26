package by.kirilldikun.onlinestoreapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) Long id,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) LocalDate date,
        @NotBlank String deliveryAddress,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) String orderStatusName,
        @NotNull Long userId,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) List<OrderItemDto> orderItemDtos,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) BigDecimal total

) {

}
