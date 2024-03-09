package by.kirilldikun.onlinestoreapi.dto;

public record UserProductDto(
        Long userId,
        Long productId,
        ProductDto productDto,
        Boolean isInCart,
        Boolean isInFavorite
) {

}
