package by.kirilldikun.onlinestoreapi.service;

import by.kirilldikun.onlinestoreapi.dto.ImageDto;

import java.util.List;

public interface ImageService {

    List<ImageDto> saveProductImages(List<ImageDto> imageDtos, Long productId);

    void deleteProductImages(Long productId);
}
