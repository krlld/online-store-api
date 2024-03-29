package by.kirilldikun.onlinestoreapi.controller;

import by.kirilldikun.onlinestoreapi.dto.FavoriteItemDto;
import by.kirilldikun.onlinestoreapi.service.FavoriteItemService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/favorite-items")
@RequiredArgsConstructor
public class FavoriteItemController {

    private final FavoriteItemService favoriteItemService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<FavoriteItemDto> findAllByUserId(@PathVariable Long userId, Pageable pageable) {
        return favoriteItemService.findAllByUserId(userId, pageable);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeFavoriteStatus(@Valid @RequestBody FavoriteItemDto favoriteItemDto) {
        favoriteItemService.changeFavoriteStatus(favoriteItemDto);
    }
}
