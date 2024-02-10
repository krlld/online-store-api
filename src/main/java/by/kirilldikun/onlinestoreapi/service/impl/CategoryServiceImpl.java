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

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDto> findAll(String query, Pageable pageable) {
        return categoryRepository.findAllByNameContainingIgnoreCase(query, pageable)
                .map(categoryMapper::toCategoryDto);
    }

    @Override
    @Transactional
    public CategoryDto save(CategoryDto categoryDto) {
        Long id = categoryDto.getId();
        String name = categoryDto.getName();
        Optional<Category> foundCategory = categoryRepository.findByName(name);
        if (Objects.isNull(id)) {
            if (foundCategory.isPresent()) {
                throw new AlreadyExistsException("Category with name: %s already exists".formatted(name));
            }
        } else {
            if (foundCategory.isPresent() && !foundCategory.get().getId().equals(id)) {
                throw new AlreadyExistsException("Category with name: %s already exists".formatted(name));
            }
            if (!categoryRepository.existsById(id)) {
                throw new NotFoundException("Category with id: %d not found".formatted(id));
            }
        }
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
