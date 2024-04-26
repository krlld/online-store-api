package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.FavoriteItemDto;
import by.kirilldikun.onlinestoreapi.entity.FavoriteItem;
import by.kirilldikun.onlinestoreapi.exceptions.NotFoundException;
import by.kirilldikun.onlinestoreapi.mapper.FavoriteItemMapper;
import by.kirilldikun.onlinestoreapi.repository.FavoriteItemRepository;
import by.kirilldikun.onlinestoreapi.repository.ProductRepository;
import by.kirilldikun.onlinestoreapi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class FavoriteItemServiceImplTest {

    @Mock
    private FavoriteItemRepository favoriteItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private FavoriteItemMapper favoriteItemMapper;

    @InjectMocks
    private FavoriteServiceImpl favoriteItemService;

    @Test
    public void testFindAllByUserId_userNotFound() {
        // Создание тестовых данных
        Long userId = 1L;
        Pageable pageable = Mockito.mock(Pageable.class);

        // Настройка поведения моков
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        // Выполнение метода, который тестируем и проверка исключения
        Assertions.assertThrows(NotFoundException.class, () -> favoriteItemService.findAllByUserId(userId, pageable));

        // Проверка вызовов методов
        Mockito.verify(userRepository, Mockito.times(1)).existsById(userId);
        Mockito.verify(favoriteItemRepository, Mockito.never()).findAllByIdUserId(Mockito.anyLong(), Mockito.any());
    }

    @Test
    public void testChangeFavoriteStatus_userAndProductExist_itemDeleted() {
        // Создание тестовых данных
        FavoriteItemDto favoriteItemDto = new FavoriteItemDto(1L, 2L, null);

        // Настройка поведения моков
        Mockito.when(userRepository.existsById(favoriteItemDto.userId())).thenReturn(true);
        Mockito.when(productRepository.existsById(favoriteItemDto.productId())).thenReturn(true);
        Mockito.when(favoriteItemRepository.deleteByIdUserIdAndIdProductId(favoriteItemDto.userId(), favoriteItemDto.productId())).thenReturn(1);

        // Выполнение метода, который тестируем
        favoriteItemService.changeFavoriteStatus(favoriteItemDto);

        // Проверка вызовов методов
        Mockito.verify(userRepository, Mockito.times(1)).existsById(favoriteItemDto.userId());
        Mockito.verify(productRepository, Mockito.times(1)).existsById(favoriteItemDto.productId());
        Mockito.verify(favoriteItemRepository, Mockito.times(1)).deleteByIdUserIdAndIdProductId(favoriteItemDto.userId(), favoriteItemDto.productId());
        Mockito.verify(favoriteItemRepository, Mockito.never()).save(Mockito.any(FavoriteItem.class));
    }

    @Test
    public void testChangeFavoriteStatus_userAndProductExist_itemSaved() {
        // Создание тестовых данных
        FavoriteItemDto favoriteItemDto = new FavoriteItemDto(1L, 2L, null);

        // Настройка поведения моков
        Mockito.when(userRepository.existsById(favoriteItemDto.userId())).thenReturn(true);
        Mockito.when(productRepository.existsById(favoriteItemDto.productId())).thenReturn(true);
        Mockito.when(favoriteItemRepository.deleteByIdUserIdAndIdProductId(favoriteItemDto.userId(), favoriteItemDto.productId())).thenReturn(0);
        Mockito.when(favoriteItemMapper.toFavoriteItem(favoriteItemDto)).thenReturn(Mockito.mock(FavoriteItem.class));

        // Выполнение метода, который тестируем
        favoriteItemService.changeFavoriteStatus(favoriteItemDto);

        // Проверка вызовов методов
        Mockito.verify(userRepository, Mockito.times(1)).existsById(favoriteItemDto.userId());
        Mockito.verify(productRepository, Mockito.times(1)).existsById(favoriteItemDto.productId());
        Mockito.verify(favoriteItemRepository, Mockito.times(1)).deleteByIdUserIdAndIdProductId(favoriteItemDto.userId(), favoriteItemDto.productId());
        Mockito.verify(favoriteItemRepository, Mockito.times(1)).save(Mockito.any(FavoriteItem.class));
    }

    @Test
    public void testChangeFavoriteStatus_userNotFound() {
        // Создание тестовых данных
        FavoriteItemDto favoriteItemDto = new FavoriteItemDto(1L, null, null);

        // Настройка поведения моков
        Mockito.when(userRepository.existsById(favoriteItemDto.userId())).thenReturn(false);

        // Выполнение метода, который тестируем и проверка исключения
        Assertions.assertThrows(NotFoundException.class, () -> favoriteItemService.changeFavoriteStatus(favoriteItemDto));

        // Проверка вызовов методов
        Mockito.verify(userRepository, Mockito.times(1)).existsById(favoriteItemDto.userId());
        Mockito.verify(productRepository, Mockito.never()).existsById(Mockito.anyLong());
        Mockito.verify(favoriteItemRepository, Mockito.never()).deleteByIdUserIdAndIdProductId(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(favoriteItemRepository, Mockito.never()).save(Mockito.any(FavoriteItem.class));
    }

    @Test
    public void testChangeFavoriteStatus_productNotFound() {
        // Создание тестовых данных
        FavoriteItemDto favoriteItemDto = new FavoriteItemDto(1L, 2L, null);

        // Настройка поведения моков
        Mockito.when(userRepository.existsById(favoriteItemDto.userId())).thenReturn(true);
        Mockito.when(productRepository.existsById(favoriteItemDto.productId())).thenReturn(false);

        // Выполнение метода, который тестируем и проверка исключения
        Assertions.assertThrows(NotFoundException.class, () -> favoriteItemService.changeFavoriteStatus(favoriteItemDto));

        // Проверка вызовов методов
        Mockito.verify(userRepository, Mockito.times(1)).existsById(favoriteItemDto.userId());
        Mockito.verify(productRepository, Mockito.times(1)).existsById(favoriteItemDto.productId());
        Mockito.verify(favoriteItemRepository, Mockito.never()).deleteByIdUserIdAndIdProductId(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(favoriteItemRepository, Mockito.never()).save(Mockito.any(FavoriteItem.class));
    }
}