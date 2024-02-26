package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.CartItemDto;
import by.kirilldikun.onlinestoreapi.entity.CartItem;
import by.kirilldikun.onlinestoreapi.exceptions.IllegalCartItemQuantityException;
import by.kirilldikun.onlinestoreapi.exceptions.NotFoundException;
import by.kirilldikun.onlinestoreapi.mapper.CartItemMapper;
import by.kirilldikun.onlinestoreapi.repository.CartItemRepository;
import by.kirilldikun.onlinestoreapi.repository.ProductRepository;
import by.kirilldikun.onlinestoreapi.repository.UserRepository;
import by.kirilldikun.onlinestoreapi.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CartItemMapper cartItemMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CartItemDto> findAllByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id: %d not found".formatted(userId));
        }
        return cartItemRepository.findAllByIdUserId(userId)
                .stream()
                .map(cartItemMapper::toCartItemDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CartItemDto> findAllByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id: %d not found".formatted(userId));
        }
        return cartItemRepository.findAllByIdUserId(userId, pageable)
                .map(cartItemMapper::toCartItemDto);
    }

    @Override
    @Transactional
    public CartItemDto addToCart(CartItemDto cartItemDto) {
        Long userId = cartItemDto.userId();
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id: %d not found".formatted(userId));
        }
        Long productId = cartItemDto.productId();
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product with id: %d not found".formatted(productId));
        }
        int quantity = cartItemDto.quantity();
        Optional<CartItem> foundCartItem = cartItemRepository.findByIdUserIdAndIdProductId(userId, productId);
        CartItem mappedCartItem = cartItemMapper.toCartItem(cartItemDto);
        if (foundCartItem.isEmpty()) {
            if (quantity <= 0) {
                throw new IllegalCartItemQuantityException("Cart item quantity should be more than 0");
            }
            System.out.println(mappedCartItem);
            return cartItemMapper.toCartItemDto(cartItemRepository.save(mappedCartItem));
        }
        int resultQuantity = foundCartItem.get().getQuantity() + quantity;
        if (resultQuantity <= 0) {
            throw new IllegalCartItemQuantityException("Cart item quantity should be more than 0");
        }
        mappedCartItem.setQuantity(resultQuantity);
        return cartItemMapper.toCartItemDto(cartItemRepository.save(mappedCartItem));
    }

    @Override
    @Transactional
    public void delete(Long userId, Long productId) {
        if (cartItemRepository.deleteByIdUserIdAndIdProductId(userId, productId) == 0) {
            throw new NotFoundException("Cart item with user id: %d and product id: %d not found"
                    .formatted(userId, productId));
        }
    }

    @Override
    @Transactional
    public void clear(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id: %d not found".formatted(userId));
        }
        cartItemRepository.deleteAllByIdUserId(userId);
    }
}
