package by.kirilldikun.onlinestoreapi.dto;

import java.math.BigDecimal;

public record UserExpensesDto(
        String username,
        BigDecimal totalExpenses
) {

}
