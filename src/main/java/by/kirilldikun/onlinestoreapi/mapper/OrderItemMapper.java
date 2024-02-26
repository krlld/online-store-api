package by.kirilldikun.onlinestoreapi.mapper;

import by.kirilldikun.onlinestoreapi.dto.CartItemDto;
import by.kirilldikun.onlinestoreapi.dto.OrderItemDto;
import by.kirilldikun.onlinestoreapi.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "categoryName", source = "product.category.name")
    OrderItemDto toOrderItemDto(OrderItem orderItem);

    @Mapping(target = "price", source = "productDto.price")
    @Mapping(target = "product.id", source = "productDto.id")
    @Mapping(target = "product.category.name", source = "productDto.categoryName")
    OrderItem toOrderItem(CartItemDto cartItemDto);

    List<OrderItem> toOrderItems(List<CartItemDto> cartItemDtos);
}
