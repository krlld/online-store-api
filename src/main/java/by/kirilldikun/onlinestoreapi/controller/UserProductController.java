package by.kirilldikun.onlinestoreapi.controller;

import by.kirilldikun.onlinestoreapi.dto.UserProductDto;
import by.kirilldikun.onlinestoreapi.service.UserProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/user-products")
@RequiredArgsConstructor
public class UserProductController {

    private final UserProductService userProductService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserProductDto> findAll(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "") String query,
            Pageable pageable) {
        return userProductService.findAll(userId, query, pageable);
    }
}
