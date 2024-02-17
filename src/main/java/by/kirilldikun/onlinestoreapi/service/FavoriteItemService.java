package by.kirilldikun.onlinestoreapi.service;

import by.kirilldikun.onlinestoreapi.dto.FavoriteItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteItemService {

    Page<FavoriteItemDto> findAllByUserId(Long userId, Pageable pageable);

    void changeFavoriteStatus(FavoriteItemDto favoriteItemDto);
}
