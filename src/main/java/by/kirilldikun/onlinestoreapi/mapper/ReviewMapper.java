package by.kirilldikun.onlinestoreapi.mapper;

import by.kirilldikun.onlinestoreapi.dto.ReviewDto;
import by.kirilldikun.onlinestoreapi.entity.Review;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ProductMapper.class)
public interface ReviewMapper {

    @InheritInverseConfiguration
    @Mapping(target = "userName", source = "user.name")
    ReviewDto toReviewDto(Review review);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "product.id", source = "productId")
    Review toReview(ReviewDto reviewDto);
}
