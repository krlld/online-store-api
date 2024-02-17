package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.FavoriteItemDto;
import by.kirilldikun.onlinestoreapi.exceptions.NotFoundException;
import by.kirilldikun.onlinestoreapi.mapper.FavoriteItemMapper;
import by.kirilldikun.onlinestoreapi.repository.FavoriteItemRepository;
import by.kirilldikun.onlinestoreapi.repository.ProductRepository;
import by.kirilldikun.onlinestoreapi.repository.UserRepository;
import by.kirilldikun.onlinestoreapi.service.FavoriteItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteItemService {

    private final FavoriteItemRepository favoriteItemRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final FavoriteItemMapper favoriteItemMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<FavoriteItemDto> findAllByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id: %d not found".formatted(userId));
        }
        return favoriteItemRepository.findAllByIdUserId(userId, pageable)
                .map(favoriteItemMapper::toFavoriteItemDto);
    }

    @Override
    @Transactional
    public void changeFavoriteStatus(FavoriteItemDto favoriteItemDto) {
        Long userId = favoriteItemDto.userId();
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id: %d not found".formatted(userId));
        }
        Long productId = favoriteItemDto.productId();
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product with id: %d not found".formatted(productId));
        }
        if (favoriteItemRepository.deleteByIdUserIdAndIdProductId(userId, productId) == 0) {
            favoriteItemRepository.save(favoriteItemMapper.toFavoriteItem(favoriteItemDto));
        }
    }
}
