package by.kirilldikun.onlinestoreapi.mapper;

import by.kirilldikun.onlinestoreapi.dto.ProductDto;
import by.kirilldikun.onlinestoreapi.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    Product toProduct(ProductDto productDto);

    ProductDto toProductDto(Product product);
}
