package by.kirilldikun.onlinestoreapi.service;

import by.kirilldikun.onlinestoreapi.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Page<OrderDto> findAllByUserId(Long userId, Pageable pageable);

    OrderDto createOrderFromUserCart(OrderDto orderDto);

    void confirmDelivery(Long orderId);
}
