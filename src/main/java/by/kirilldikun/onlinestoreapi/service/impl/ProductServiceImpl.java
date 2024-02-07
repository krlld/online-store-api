package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.ImageDto;
import by.kirilldikun.onlinestoreapi.dto.ProductDto;
import by.kirilldikun.onlinestoreapi.entity.Product;
import by.kirilldikun.onlinestoreapi.exceptions.AlreadyExistsException;
import by.kirilldikun.onlinestoreapi.exceptions.NotFoundException;
import by.kirilldikun.onlinestoreapi.mapper.ProductMapper;
import by.kirilldikun.onlinestoreapi.repository.CategoryRepository;
import by.kirilldikun.onlinestoreapi.repository.ProductRepository;
import by.kirilldikun.onlinestoreapi.service.ImageService;
import by.kirilldikun.onlinestoreapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ImageService imageService;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toProductDto);
    }

    @Override
    @Transactional
    public ProductDto save(ProductDto productDto) {
        if (!categoryRepository.existsById(productDto.getCategoryId())) {
            throw new NotFoundException("Category with id: %d not found".formatted(productDto.getCategoryId()));
        }
        if (productRepository.existsByName(productDto.getName())) {
            throw new AlreadyExistsException("Product with name: %s already exists".formatted(productDto.getName()));
        }
        Product product = productMapper.toProduct(productDto);
        System.out.println(product.getCategoryId());
        productRepository.save(product);
        List<ImageDto> imageDtos = imageService.saveProductImages(productDto.getImages(), product.getId());
        return productMapper.toProductDto(product).setImages(imageDtos);
    }

    @Override
    @Transactional
    public ProductDto update(Long id, ProductDto productDto) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product with id: %d not found".formatted(id));
        }
        if (!categoryRepository.existsById(productDto.getCategoryId())) {
            throw new NotFoundException("Category with id: %d not found".formatted(productDto.getCategoryId()));
        }
        Optional<Product> foundProduct = productRepository.findByName(productDto.getName());
        if (foundProduct.isPresent() && !foundProduct.get().getId().equals(id)) {
            throw new AlreadyExistsException("Product with name: %s already exists".formatted(productDto.getName()));
        }
        Product product = productMapper.toProduct(productDto).setId(id);
        productRepository.save(product);
        List<ImageDto> imageDtos = imageService.saveProductImages(productDto.getImages(), product.getId());
        return productMapper.toProductDto(product).setImages(imageDtos);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        imageService.deleteProductImages(id);
        if (productRepository.softDeleteById(id) == 0) {
            throw new NotFoundException("Product with id: %d not found".formatted(id));
        }
    }
}
