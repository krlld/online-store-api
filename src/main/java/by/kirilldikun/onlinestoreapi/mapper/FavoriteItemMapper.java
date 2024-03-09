package by.kirilldikun.onlinestoreapi.mapper;

import by.kirilldikun.onlinestoreapi.dto.FavoriteItemDto;
import by.kirilldikun.onlinestoreapi.entity.FavoriteItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ProductMapper.class)
public interface FavoriteItemMapper {

    @Mapping(source = "id.userId", target = "userId")
    @Mapping(source = "product", target = "productDto")
    FavoriteItemDto toFavoriteItemDto(FavoriteItem favoriteItem);

    @Mapping(source = "userId", target = "id.userId")
    @Mapping(source = "productId", target = "id.productId")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "productId", target = "product.id")
    FavoriteItem toFavoriteItem(FavoriteItemDto favoriteItemDto);
}
