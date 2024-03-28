package by.kirilldikun.onlinestoreapi.dto;

import java.math.BigDecimal;

public record CategoryRevenueDto(
        String categoryName,
        BigDecimal totalRevenue
) {

}