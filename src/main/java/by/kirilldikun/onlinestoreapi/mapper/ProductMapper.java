package by.kirilldikun.onlinestoreapi.mapper;

import by.kirilldikun.onlinestoreapi.dto.ProductDto;
import by.kirilldikun.onlinestoreapi.entity.Product;
import by.kirilldikun.onlinestoreapi.exceptions.NotFoundException;
import by.kirilldikun.onlinestoreapi.repository.CategoryRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class ProductMapper {

    protected CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public abstract Product toProduct(ProductDto productDto);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    public abstract ProductDto toProductDto(Product product);

    @AfterMapping
    public void setCategory(@MappingTarget Product product, ProductDto productDto) {
        product.setCategory(categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category with id: %d not found"
                        .formatted(productDto.getCategoryId()))));
    }
}
