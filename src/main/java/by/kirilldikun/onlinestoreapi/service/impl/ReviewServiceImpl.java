package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.ReviewDto;
import by.kirilldikun.onlinestoreapi.entity.Review;
import by.kirilldikun.onlinestoreapi.exceptions.NotFoundException;
import by.kirilldikun.onlinestoreapi.mapper.ReviewMapper;
import by.kirilldikun.onlinestoreapi.repository.ReviewRepository;
import by.kirilldikun.onlinestoreapi.service.ProductService;
import by.kirilldikun.onlinestoreapi.service.ReviewService;
import by.kirilldikun.onlinestoreapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserService userService;

    private final ProductService productService;

    private final ReviewMapper reviewMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDto> findAll(Long productId, Pageable pageable) {
        productService.findById(productId);
        return reviewRepository.findAllByProductId(productId, pageable)
                .map(reviewMapper::toReviewDto);
    }

    @Override
    @Transactional
    public ReviewDto save(ReviewDto reviewDto) {
        userService.findById(reviewDto.userId());
        productService.findById(reviewDto.productId());
        Long id = reviewDto.id();
        if (Objects.isNull(id)) {
            return saveWithoutCheck(reviewDto);
        }
        if (!reviewRepository.existsById(id)) {
            throw new NotFoundException("Review with id: %d not found".formatted(id));
        }
        return saveWithoutCheck(reviewDto);
    }

    @Transactional
    public ReviewDto saveWithoutCheck(ReviewDto reviewDto) {
        Review review = reviewMapper.toReview(reviewDto);
        reviewRepository.save(review);
        return reviewMapper.toReviewDto(review);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (reviewRepository.softDeleteById(id) == 0) {
            throw new NotFoundException("Review with id: %d not found".formatted(id));
        }
    }
}
