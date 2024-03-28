package by.kirilldikun.onlinestoreapi.controller;

import by.kirilldikun.onlinestoreapi.dto.OrderDto;
import by.kirilldikun.onlinestoreapi.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class OrderController {

    public static final String FETCH_USER_ORDERS_MAPPING = "/orders/{userId}";

    public static final String CREATE_ORDER_MAPPING = "/orders";

    public static final String CONFIRM_DELIVERY_MAPPING = "/orders/{orderId}";

    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(FETCH_USER_ORDERS_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDto> findAllByUserId(@PathVariable Long userId, Pageable pageable) {
        return orderService.findAllByUserId(userId, pageable);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping(CREATE_ORDER_MAPPING)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrderFromUserCart(@Valid @RequestBody OrderDto orderDto) {
        return orderService.createOrderFromUserCart(orderDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PatchMapping(CONFIRM_DELIVERY_MAPPING)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmDelivery(@PathVariable Long orderId) {
        orderService.confirmDelivery(orderId);
    }

}
