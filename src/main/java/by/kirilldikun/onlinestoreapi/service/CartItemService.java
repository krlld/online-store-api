package by.kirilldikun.onlinestoreapi.service;

import by.kirilldikun.onlinestoreapi.dto.CartItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartItemService {

    Page<CartItemDto> findAllByUserId(Long userId, Pageable pageable);

    CartItemDto addToCart(CartItemDto cartItemDto);

    void delete(Long userId, Long productId);
}
