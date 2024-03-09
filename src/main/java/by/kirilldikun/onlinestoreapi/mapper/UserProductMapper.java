package by.kirilldikun.onlinestoreapi.mapper;

import by.kirilldikun.onlinestoreapi.dto.ProductDto;
import by.kirilldikun.onlinestoreapi.dto.UserProductDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.Set;

@Mapper
public interface UserProductMapper {

    default Page<UserProductDto> toUserProductDtos(
            Long userId,
            Page<ProductDto> productDtos,
            Set<Long> userCartItems,
            Set<Long> userFavoriteItems) {
        return productDtos
                .map(productDto -> new UserProductDto(
                                userId,
                                productDto.getId(),
                                productDto,
                                userCartItems.contains(productDto.getId()),
                                userFavoriteItems.contains(productDto.getId())
                        )
                );
    }
}
