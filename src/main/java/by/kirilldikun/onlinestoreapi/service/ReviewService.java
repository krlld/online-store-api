package by.kirilldikun.onlinestoreapi.service;

import by.kirilldikun.onlinestoreapi.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    Page<ReviewDto> findAll(Long productId, Pageable pageable);

    ReviewDto save(ReviewDto reviewDto);

    void delete(Long id);
}
