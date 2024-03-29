package by.kirilldikun.onlinestoreapi.controller;

import by.kirilldikun.onlinestoreapi.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping(value = "/price-history/{productId}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getPriceHistory(@PathVariable Long productId) {
        return statisticService.createPriceChart(productId);
    }

    @GetMapping(value = "/categories-revenue", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getCategoryRevenue() {
        return statisticService.createCategoriesRevenueChart();
    }

    @GetMapping(value = "/users-expenses", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getUsersExpenses(@RequestParam(defaultValue = "5") @Range(min = 1, max = 100) Integer limit) {
        return statisticService.createUsersExpensesChart(limit);
    }
}
