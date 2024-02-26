package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.CartItemDto;
import by.kirilldikun.onlinestoreapi.dto.OrderDto;
import by.kirilldikun.onlinestoreapi.entity.Order;
import by.kirilldikun.onlinestoreapi.entity.OrderItem;
import by.kirilldikun.onlinestoreapi.entity.OrderStatusEnum;
import by.kirilldikun.onlinestoreapi.exceptions.EmptyCartException;
import by.kirilldikun.onlinestoreapi.exceptions.NotFoundException;
import by.kirilldikun.onlinestoreapi.mapper.OrderItemMapper;
import by.kirilldikun.onlinestoreapi.mapper.OrderMapper;
import by.kirilldikun.onlinestoreapi.repository.OrderItemRepository;
import by.kirilldikun.onlinestoreapi.repository.OrderRepository;
import by.kirilldikun.onlinestoreapi.service.CartItemService;
import by.kirilldikun.onlinestoreapi.service.OrderService;
import by.kirilldikun.onlinestoreapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final UserService userService;

    private final CartItemService cartItemService;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> findAllByUserId(Long userId, Pageable pageable) {
        userService.findById(userId);
        Page<Order> all = orderRepository.findAllByUserId(userId, pageable);
        all.get().forEach(order -> System.out.println(order.getOrderItems()));
        return all
                .map(orderMapper::toOrderDto);
    }

    @Override
    @Transactional
    public OrderDto createOrderFromUserCart(OrderDto orderDto) {
        userService.findById(orderDto.userId());
        List<CartItemDto> cartItemDtos = cartItemService.findAllByUserId(orderDto.userId());
        if (cartItemDtos.isEmpty()) {
            throw new EmptyCartException("Cart should contains items");
        }
        Order order = orderMapper.toOrder(orderDto)
                .setDate(LocalDate.now())
                .setOrderStatusName(OrderStatusEnum.ON_WAY.toString());
        orderRepository.save(order);
        List<OrderItem> orderItems = orderItemMapper.toOrderItems(cartItemDtos);
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        orderItemRepository.saveAll(orderItems);
        order.setOrderItems(orderItems);
        cartItemService.clear(orderDto.userId());
        return orderMapper.toOrderDto(order);
    }

    @Override
    @Transactional
    public void confirmDelivery(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order with id: %d not found".formatted(orderId)));
        order.setOrderStatusName(OrderStatusEnum.DELIVERED.toString());
    }
}
