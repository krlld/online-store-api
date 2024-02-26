package by.kirilldikun.onlinestoreapi.dto;

import java.math.BigDecimal;

public record OrderItemDto(
        Long id,
        BigDecimal price,
        Integer quantity,
        String productName,
        String categoryName
) {

}
