package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.ImageDto;
import by.kirilldikun.onlinestoreapi.entity.Image;
import by.kirilldikun.onlinestoreapi.mapper.ImageMapper;
import by.kirilldikun.onlinestoreapi.repository.ImageRepository;
import by.kirilldikun.onlinestoreapi.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    @Override
    @Transactional
    public List<ImageDto> saveProductImages(List<ImageDto> imageDtos, Long productId) {
        deleteProductImages(productId);
        imageDtos.forEach(imageDto -> imageDto.setProductId(productId));
        List<Image> images = imageDtos
                .stream()
                .map(imageMapper::toImage)
                .toList();
        return imageRepository.saveAll(images)
                .stream()
                .map(imageMapper::toImageDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteProductImages(Long productId) {
        imageRepository.softDeleteAllByProductId(productId);
    }
}
