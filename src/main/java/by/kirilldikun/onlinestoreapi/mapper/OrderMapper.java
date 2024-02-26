package by.kirilldikun.onlinestoreapi.mapper;

import by.kirilldikun.onlinestoreapi.dto.OrderDto;
import by.kirilldikun.onlinestoreapi.entity.Order;
import by.kirilldikun.onlinestoreapi.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.List;

@Mapper(uses = OrderItemMapper.class)
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "total", source = "orderItems", qualifiedByName = "calculateTotal")
    @Mapping(target = "orderItemDtos", source = "orderItems")
    OrderDto toOrderDto(Order order);

    @Mapping(target = "user.id", source = "userId")
    Order toOrder(OrderDto orderDto);

    @Named("calculateTotal")
    default BigDecimal calculateTotal(List<OrderItem> orderItems) {
        return orderItems
                .stream()
                .map(orderItem -> orderItem.getPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                )
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
