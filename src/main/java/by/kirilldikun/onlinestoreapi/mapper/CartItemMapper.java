package by.kirilldikun.onlinestoreapi.mapper;

import by.kirilldikun.onlinestoreapi.dto.CartItemDto;
import by.kirilldikun.onlinestoreapi.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ProductMapper.class)
public interface CartItemMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product", target = "productDto")
    CartItemDto toCartItemDto(CartItem cartItem);

    @Mapping(source = "userId", target = "id.userId")
    @Mapping(source = "productId", target = "id.productId")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "productId", target = "product.id")
    CartItem toCartItem(CartItemDto cartItemDto);
}
