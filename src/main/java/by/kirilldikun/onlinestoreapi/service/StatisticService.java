package by.kirilldikun.onlinestoreapi.service;

public interface StatisticService {

    byte[] createPriceChart(Long productId);

    byte[] createCategoriesRevenueChart();

    byte[] createUsersExpensesChart(int limit);
}
