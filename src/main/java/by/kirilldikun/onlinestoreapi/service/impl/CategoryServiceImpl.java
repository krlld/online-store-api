package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.CategoryDto;
import by.kirilldikun.onlinestoreapi.entity.Category;
import by.kirilldikun.onlinestoreapi.exceptions.AlreadyExistsException;
import by.kirilldikun.onlinestoreapi.exceptions.NotFoundException;
import by.kirilldikun.onlinestoreapi.mapper.CategoryMapper;
import by.kirilldikun.onlinestoreapi.repository.CategoryRepository;
import by.kirilldikun.onlinestoreapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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
            throw new AlreadyExistsException("Category with name: %s already exists".formatted(name));
        }
        categoryDto.setId(null);
        Category category = categoryMapper.toCategory(categoryDto);
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category with id: %d not found".formatted(id));
        }
        String name = categoryDto.getName();
        if (categoryRepository.existsByName(name)) {
            throw new AlreadyExistsException("Category with name: %s already exists".formatted(name));
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
            throw new NotFoundException("Category with id: %d not found".formatted(id));
        }
    }
}
