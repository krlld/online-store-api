package by.kirilldikun.onlinestoreapi.mapper;

import by.kirilldikun.onlinestoreapi.dto.ImageDto;
import by.kirilldikun.onlinestoreapi.entity.Image;
import org.mapstruct.Mapper;

@Mapper
public interface ImageMapper {

    Image toImage(ImageDto imageDto);

    ImageDto toImageDto(Image image);
}
