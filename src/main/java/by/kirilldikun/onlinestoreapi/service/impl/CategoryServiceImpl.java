package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.CategoryDto;
import by.kirilldikun.onlinestoreapi.entity.Category;
import by.kirilldikun.onlinestoreapi.exceptions.CategoryAlreadyExistsException;
import by.kirilldikun.onlinestoreapi.exceptions.CategoryNotFoundException;
import by.kirilldikun.onlinestoreapi.mapper.CategoryMapper;
import by.kirilldikun.onlinestoreapi.repository.CategoryRepository;
import by.kirilldikun.onlinestoreapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toCategoryDto);
    }

    @Override
    @Transactional
    public CategoryDto save(CategoryDto categoryDto) {
        String name = categoryDto.getName();
        if (categoryRepository.existsByName(name)) {
            log.warn("Attempt to save a existent category with name: %s".formatted(name));
            throw new CategoryAlreadyExistsException("Category with name: %s already exists".formatted(name));
        }
        categoryDto.setId(null);
        Category category = categoryMapper.toCategory(categoryDto);
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        if (!categoryRepository.existsById(id)) {
            log.warn("Attempt to access a non-existent category by id: %d".formatted(id));
            throw new CategoryNotFoundException("Category with id: %d not found".formatted(id));
        }
        String name = categoryDto.getName();
        if (categoryRepository.existsByName(name)) {
            log.warn("Attempt to save a existent category with name: %s".formatted(name));
            throw new CategoryAlreadyExistsException("Category with name: %s already exists".formatted(name));
        }
        categoryDto.setId(id);
        Category category = categoryMapper.toCategory(categoryDto);
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        int updatedRows = categoryRepository.softDeleteById(id);
        if (updatedRows == 0) {
            log.warn("Attempt to access a non-existent category by id: %d".formatted(id));
            throw new CategoryNotFoundException("Category with id: %d not found".formatted(id));
        }
    }
}
