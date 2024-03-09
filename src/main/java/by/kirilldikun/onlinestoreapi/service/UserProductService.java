package by.kirilldikun.onlinestoreapi.service;

import by.kirilldikun.onlinestoreapi.dto.UserProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserProductService {

    Page<UserProductDto> findAll(Long userId, String query, Pageable pageable);
}
