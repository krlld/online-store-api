package by.kirilldikun.onlinestoreapi.service;

import by.kirilldikun.onlinestoreapi.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductDto> findAll(String query, Pageable pageable);

    ProductDto findById(Long id);

    ProductDto save(ProductDto productDto);

    void delete(Long id);
}
