package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.ProductDto;
import by.kirilldikun.onlinestoreapi.dto.UserProductDto;
import by.kirilldikun.onlinestoreapi.mapper.UserProductMapper;
import by.kirilldikun.onlinestoreapi.repository.UserProductRepository;
import by.kirilldikun.onlinestoreapi.service.ProductService;
import by.kirilldikun.onlinestoreapi.service.UserProductService;
import by.kirilldikun.onlinestoreapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserProductServiceImpl implements UserProductService {

    private final UserService userService;

    private final ProductService productService;

    private final UserProductRepository userProductRepository;

    private final UserProductMapper userProductMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserProductDto> findAll(Long userId, String query, Pageable pageable) {
        userService.findById(userId);
        Page<ProductDto> productDtos = productService.findAll(query, pageable);
        Set<Long> userCartItems = new HashSet<>(userProductRepository
                .findAllProductIdsThatInUserCart(userId, query, pageable));
        Set<Long> userFavoriteItems = new HashSet<>(userProductRepository
                .findAllProductIdsThatInUserFavorite(userId, query, pageable));
        return userProductMapper.toUserProductDtos(userId, productDtos, userCartItems, userFavoriteItems);
    }
}
