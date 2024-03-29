package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.CategoryDto;
import by.kirilldikun.onlinestoreapi.entity.Category;
import by.kirilldikun.onlinestoreapi.exceptions.AlreadyExistsException;
import by.kirilldikun.onlinestoreapi.exceptions.NotFoundException;
import by.kirilldikun.onlinestoreapi.mapper.CategoryMapper;
import by.kirilldikun.onlinestoreapi.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void testFindAll() {
        // Создание тестовых данных
        String query = "test";
        Pageable pageable = PageRequest.of(0, 10);
        List<Category> categories = Arrays.asList(new Category(), new Category());
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());
        List<CategoryDto> categoryDtos = Arrays.asList(new CategoryDto(), new CategoryDto());

        // Настройка поведения моков
        Mockito.when(categoryRepository.findAllByNameContainingIgnoreCase(query, pageable)).thenReturn(categoryPage);
        Mockito.when(categoryMapper.toCategoryDto(Mockito.any(Category.class))).thenReturn(new CategoryDto());

        // Выполнение метода, который тестируем
        Page<CategoryDto> result = categoryService.findAll(query, pageable);

        // Проверка результатов
        Assertions.assertEquals(categoryDtos.size(), result.getContent().size());
        Mockito.verify(categoryRepository, Mockito.times(1)).findAllByNameContainingIgnoreCase(query, pageable);
        Mockito.verify(categoryMapper, Mockito.times(categories.size())).toCategoryDto(Mockito.any(Category.class));
    }

    @Test
    public void testSave_newCategory() {
        // Создание тестовых данных
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Test Category");
        Category category = new Category();
        category.setName("Test Category");

        // Настройка поведения моков
        Mockito.when(categoryRepository.findByName("Test Category")).thenReturn(Optional.empty());
        Mockito.when(categoryMapper.toCategory(categoryDto)).thenReturn(category);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        Mockito.when(categoryMapper.toCategoryDto(category)).thenReturn(categoryDto);

        // Выполнение метода, который тестируем
        CategoryDto result = categoryService.save(categoryDto);

        // Проверка результатов
        Assertions.assertEquals(categoryDto, result);
        Mockito.verify(categoryRepository, Mockito.times(1)).findByName("Test Category");
        Mockito.verify(categoryRepository, Mockito.times(1)).save(category);
    }

    @Test
    public void testSave_existingCategory() {
        // Создание тестовых данных
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(2L);
        categoryDto.setName("Test Category");
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        // Настройка поведения моков
        Mockito.when(categoryRepository.findByName("Test Category")).thenReturn(Optional.of(category));

        // Выполнение метода, который тестируем
        Assertions.assertThrows(AlreadyExistsException.class, () -> categoryService.save(categoryDto));
    }

    @Test
    public void testSave_categoryNotFound() {
        // Создание тестовых данных
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Test Category");

        // Настройка поведения моков
        Mockito.when(categoryRepository.findByName("Test Category")).thenReturn(Optional.empty());
        Mockito.when(categoryRepository.existsById(1L)).thenReturn(false);

        // Выполнение метода, который тестируем
        Assertions.assertThrows(NotFoundException.class, () -> categoryService.save(categoryDto));
    }

    @Test
    public void testDelete_categoryFound() {
        // Создание тестовых данных
        Long categoryId = 1L;

        // Настройка поведения моков
        Mockito.when(categoryRepository.softDeleteById(categoryId)).thenReturn(1);

        // Выполнение метода, который тестируем
        categoryService.delete(categoryId);

        // Проверка вызова методов
        Mockito.verify(categoryRepository, Mockito.times(1)).softDeleteById(categoryId);
    }

    @Test
    public void testDelete_categoryNotFound() {
        // Создание тестовых данных
        Long categoryId = 1L;

        // Настройка поведения моков
        Mockito.when(categoryRepository.softDeleteById(categoryId)).thenReturn(0);

        // Выполнение метода, который тестируем
        Assertions.assertThrows(NotFoundException.class, () -> categoryService.delete(categoryId));
    }
}