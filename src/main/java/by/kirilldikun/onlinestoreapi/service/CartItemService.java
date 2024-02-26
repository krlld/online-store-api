package by.kirilldikun.onlinestoreapi.service;

import by.kirilldikun.onlinestoreapi.dto.CartItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartItemService {

    List<CartItemDto> findAllByUserId(Long userId);

    Page<CartItemDto> findAllByUserId(Long userId, Pageable pageable);

    CartItemDto addToCart(CartItemDto cartItemDto);

    void delete(Long userId, Long productId);

    void clear(Long userId);
}
