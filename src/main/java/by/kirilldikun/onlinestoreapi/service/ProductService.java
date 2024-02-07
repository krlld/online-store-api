package by.kirilldikun.onlinestoreapi.service;

import by.kirilldikun.onlinestoreapi.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductDto> findAll(Pageable pageable);

    ProductDto save(ProductDto productDto);

    ProductDto update(Long id, ProductDto productDto);

    void delete(Long id);
}
