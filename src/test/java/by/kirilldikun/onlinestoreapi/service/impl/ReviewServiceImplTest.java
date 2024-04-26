package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.ProductDto;
import by.kirilldikun.onlinestoreapi.dto.ReviewDto;
import by.kirilldikun.onlinestoreapi.dto.UserDto;
import by.kirilldikun.onlinestoreapi.entity.Review;
import by.kirilldikun.onlinestoreapi.exceptions.NotFoundException;
import by.kirilldikun.onlinestoreapi.mapper.ReviewMapper;
import by.kirilldikun.onlinestoreapi.repository.ReviewRepository;
import by.kirilldikun.onlinestoreapi.service.ProductService;
import by.kirilldikun.onlinestoreapi.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    public void testSave_ReviewWithoutId() {
        // Создание тестовых данных
        ReviewDto reviewDto = new ReviewDto(null, null, null, null, 1L, null, 1L, null);

        // Настройка поведения моков
        Mockito.when(userService.findById(reviewDto.userId())).thenReturn(Mockito.mock(UserDto.class));
        Mockito.when(productService.findById(reviewDto.productId())).thenReturn(Mockito.mock(ProductDto.class));
        Review savedReview = Mockito.mock(Review.class);
        ReviewDto savedReviewDto = Mockito.mock(ReviewDto.class);
        Mockito.when(reviewMapper.toReview(reviewDto)).thenReturn(savedReview);
        Mockito.when(reviewRepository.save(savedReview)).thenReturn(savedReview);
        Mockito.when(reviewMapper.toReviewDto(savedReview)).thenReturn(savedReviewDto);

        // Выполнение метода, который тестируем
        ReviewDto result = reviewService.save(reviewDto);

        // Проверка результатов и вызовов методов
        Assertions.assertEquals(savedReviewDto, result);
        Mockito.verify(userService, Mockito.times(1)).findById(reviewDto.userId());
        Mockito.verify(productService, Mockito.times(1)).findById(reviewDto.productId());
        Mockito.verify(reviewMapper, Mockito.times(1)).toReview(reviewDto);
        Mockito.verify(reviewRepository, Mockito.times(1)).save(savedReview);
        Mockito.verify(reviewMapper, Mockito.times(1)).toReviewDto(savedReview);
    }

    @Test
    public void testSave_ReviewWithExistingId() {
        // Создание тестовых данных
        Long reviewId = 1L;
        ReviewDto reviewDto = new ReviewDto(reviewId, null, null, null, 1L, null, 1L, null);

        // Настройка поведения моков
        Mockito.when(userService.findById(reviewDto.userId())).thenReturn(Mockito.mock(UserDto.class));
        Mockito.when(productService.findById(reviewDto.productId())).thenReturn(Mockito.mock(ProductDto.class));
        Mockito.when(reviewRepository.existsById(reviewId)).thenReturn(true);
        Review savedReview = Mockito.mock(Review.class);
        ReviewDto savedReviewDto = Mockito.mock(ReviewDto.class);
        Mockito.when(reviewMapper.toReview(reviewDto)).thenReturn(savedReview);
        Mockito.when(reviewRepository.save(savedReview)).thenReturn(savedReview);
        Mockito.when(reviewMapper.toReviewDto(savedReview)).thenReturn(savedReviewDto);

        // Выполнение метода, который тестируем
        ReviewDto result = reviewService.save(reviewDto);

        // Проверка результатов и вызовов методов
        Assertions.assertEquals(savedReviewDto, result);
        Mockito.verify(userService, Mockito.times(1)).findById(reviewDto.userId());
        Mockito.verify(productService, Mockito.times(1)).findById(reviewDto.productId());
        Mockito.verify(reviewRepository, Mockito.times(1)).existsById(reviewId);
        Mockito.verify(reviewMapper, Mockito.times(1)).toReview(reviewDto);
        Mockito.verify(reviewRepository, Mockito.times(1)).save(savedReview);
        Mockito.verify(reviewMapper, Mockito.times(1)).toReviewDto(savedReview);
    }

    @Test
    public void testSave_ReviewWithNonExistingId() {
        // Создание тестовых данных
        Long reviewId = 1L;
        ReviewDto reviewDto = new ReviewDto(reviewId, null, null, null, 1L, null, 1L, null);

        // Настройка поведения моков
        Mockito.when(userService.findById(reviewDto.userId())).thenReturn(Mockito.mock(UserDto.class));
        Mockito.when(productService.findById(reviewDto.productId())).thenReturn(Mockito.mock(ProductDto.class));
        Mockito.when(reviewRepository.existsById(reviewId)).thenReturn(false);

        // Выполнение метода, который тестируем
        Assertions.assertThrows(NotFoundException.class, () -> reviewService.save(reviewDto));

        // Проверка вызовов методов
        Mockito.verify(userService, Mockito.times(1)).findById(reviewDto.userId());
        Mockito.verify(productService, Mockito.times(1)).findById(reviewDto.productId());
        Mockito.verify(reviewRepository, Mockito.times(1)).existsById(reviewId);
        Mockito.verify(reviewRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(reviewMapper, Mockito.never()).toReviewDto(Mockito.any());
    }

    @Test
    public void testDelete_ReviewExists() {
        // Создание тестовых данных
        Long reviewId = 1L;

        // Настройка поведения моков
        Mockito.when(reviewRepository.softDeleteById(reviewId)).thenReturn(1);

        // Выполнение метода, который тестируем
        reviewService.delete(reviewId);

        // Проверка вызовов методов
        Mockito.verify(reviewRepository, Mockito.times(1)).softDeleteById(reviewId);
    }

    @Test
    public void testDelete_ReviewDoesNotExist() {
        // Создание тестовых данных
        Long reviewId = 1L;

        // Настройка поведения моков
        Mockito.when(reviewRepository.softDeleteById(reviewId)).thenReturn(0);

        // Выполнение метода, который тестируем
        Assertions.assertThrows(NotFoundException.class, () -> reviewService.delete(reviewId));

        // Проверка вызовов методов
        Mockito.verify(reviewRepository, Mockito.times(1)).softDeleteById(reviewId);
    }
}