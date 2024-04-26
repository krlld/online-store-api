package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.CartItemDto;
import by.kirilldikun.onlinestoreapi.entity.CartItem;
import by.kirilldikun.onlinestoreapi.entity.Product;
import by.kirilldikun.onlinestoreapi.entity.User;
import by.kirilldikun.onlinestoreapi.entity.UserProductId;
import by.kirilldikun.onlinestoreapi.exceptions.NotFoundException;
import by.kirilldikun.onlinestoreapi.mapper.CartItemMapper;
import by.kirilldikun.onlinestoreapi.repository.CartItemRepository;
import by.kirilldikun.onlinestoreapi.repository.ProductRepository;
import by.kirilldikun.onlinestoreapi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartItemMapper cartItemMapper;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @Test
    public void testFindAllByUserId_ExistingUser_ShouldReturnListOfCartItems() {
        // Arrange
        Long userId = 1L;
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem());
        cartItems.add(new CartItem());

        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(cartItemRepository.findAllByIdUserId(userId)).thenReturn(cartItems);
        Mockito.when(cartItemMapper.toCartItemDto(ArgumentMatchers.any(CartItem.class)))
                .thenReturn(new CartItemDto(null, null, null, null));

        // Act
        List<CartItemDto> result = cartItemService.findAllByUserId(userId);

        // Assert
        Assertions.assertEquals(2, result.size());
        Mockito.verify(userRepository, Mockito.times(1)).existsById(userId);
        Mockito.verify(cartItemRepository, Mockito.times(1)).findAllByIdUserId(userId);
        Mockito.verify(cartItemMapper, Mockito.times(2)).toCartItemDto(ArgumentMatchers.any(CartItem.class));
    }

    @Test
    public void testFindAllByUserId_NonExistingUser_ShouldThrowNotFoundException() {
        // Arrange
        Long userId = 1L;

        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            cartItemService.findAllByUserId(userId);
        });

        Mockito.verify(userRepository, Mockito.times(1)).existsById(userId);
        Mockito.verify(cartItemRepository, Mockito.never()).findAllByIdUserId(userId);
        Mockito.verify(cartItemMapper, Mockito.never()).toCartItemDto(ArgumentMatchers.any(CartItem.class));
    }

    @Test
    public void testFindAllByUserId_ExistingUserWithPageable_ShouldReturnPageOfCartItems() {
        // Arrange
        Long userId = 1L;
        Pageable pageable = Mockito.mock(Pageable.class);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem());
        cartItems.add(new CartItem());
        Page<CartItem> cartItemPage = new PageImpl<>(cartItems);

        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(cartItemRepository.findAllByIdUserId(userId, pageable)).thenReturn(cartItemPage);
        Mockito.when(cartItemMapper.toCartItemDto(ArgumentMatchers.any(CartItem.class)))
                .thenReturn(new CartItemDto(null, null, null, null));

        // Act
        Page<CartItemDto> result = cartItemService.findAllByUserId(userId, pageable);

        // Assert
        Assertions.assertEquals(2, result.getTotalElements());
        Mockito.verify(userRepository, Mockito.times(1)).existsById(userId);
        Mockito.verify(cartItemRepository, Mockito.times(1)).findAllByIdUserId(userId, pageable);
        Mockito.verify(cartItemMapper, Mockito.times(2)).toCartItemDto(ArgumentMatchers.any(CartItem.class));
    }

    @Test
    public void testFindAllByUserId_NonExistingUserWithPageable_ShouldThrowNotFoundException() {
        // Arrange
        Long userId = 1L;
        Pageable pageable = Mockito.mock(Pageable.class);

        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            cartItemService.findAllByUserId(userId, pageable);
        });

        Mockito.verify(userRepository, Mockito.times(1)).existsById(userId);
        Mockito.verify(cartItemRepository, Mockito.never()).findAllByIdUserId(userId, pageable);
        Mockito.verify(cartItemMapper, Mockito.never()).toCartItemDto(ArgumentMatchers.any(CartItem.class));
    }

    @Test
    public void testAddToCart_ValidCartItemDto_ShouldReturnCartItemDto() {
        // Arrange
        CartItemDto cartItemDto = new CartItemDto(3, 1L, 2L, null);
        CartItem cartItem = new CartItem();
        UserProductId userProductId = new UserProductId();
        userProductId.setUserId(cartItemDto.userId());
        userProductId.setProductId(cartItemDto.productId());
        cartItem.setId(userProductId);

        Mockito.when(userRepository.existsById(cartItemDto.userId())).thenReturn(true);
        Mockito.when(productRepository.existsById(cartItemDto.productId())).thenReturn(true);
        Mockito.when(cartItemRepository.findByIdUserIdAndIdProductId(cartItemDto.userId(), cartItemDto.productId()))
                .thenReturn(Optional.empty());
        Mockito.when(cartItemMapper.toCartItem(cartItemDto)).thenReturn(cartItem);
        Mockito.when(cartItemMapper.toCartItemDto(cartItem)).thenReturn(cartItemDto);
        Mockito.when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        // Act
        CartItemDto result = cartItemService.addToCart(cartItemDto);

        // Assert
        Assertions.assertEquals(cartItemDto, result);
        Mockito.verify(userRepository, Mockito.times(1)).existsById(cartItemDto.userId());
        Mockito.verify(productRepository, Mockito.times(1)).existsById(cartItemDto.productId());
        Mockito.verify(cartItemRepository, Mockito.times(1))
                .findByIdUserIdAndIdProductId(cartItemDto.userId(), cartItemDto.productId());
        Mockito.verify(cartItemMapper, Mockito.times(1)).toCartItem(cartItemDto);
        Mockito.verify(cartItemMapper, Mockito.times(1)).toCartItemDto(cartItem);
        Mockito.verify(cartItemRepository, Mockito.times(1)).save(cartItem);
    }

    @Test
    public void testAddToCart_ExistingCartItemDto_ShouldReturnUpdatedCartItemDto() {
        // Arrange
        CartItemDto cartItemDto = new CartItemDto(3, 1L, 2L, null);
        CartItem cartItem = new CartItem();
        UserProductId userProductId = new UserProductId();
        userProductId.setUserId(cartItemDto.userId());
        userProductId.setProductId(cartItemDto.productId());
        cartItem.setId(userProductId);
        CartItem existingCartItem = new CartItem();
        existingCartItem.setQuantity(5);

        Mockito.when(userRepository.existsById(cartItemDto.userId())).thenReturn(true);
        Mockito.when(productRepository.existsById(cartItemDto.productId())).thenReturn(true);
        Mockito.when(cartItemRepository.findByIdUserIdAndIdProductId(cartItemDto.userId(), cartItemDto.productId()))
                .thenReturn(Optional.of(existingCartItem));
        Mockito.when(cartItemMapper.toCartItem(cartItemDto)).thenReturn(cartItem);
        Mockito.when(cartItemMapper.toCartItemDto(cartItem)).thenReturn(cartItemDto);
        Mockito.when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        // Act
        CartItemDto result = cartItemService.addToCart(cartItemDto);

        // Assert
        Assertions.assertEquals(cartItemDto, result);
        Mockito.verify(userRepository, Mockito.times(1)).existsById(cartItemDto.userId());
        Mockito.verify(productRepository, Mockito.times(1)).existsById(cartItemDto.productId());
        Mockito.verify(cartItemRepository, Mockito.times(1))
                .findByIdUserIdAndIdProductId(cartItemDto.userId(), cartItemDto.productId());
        Mockito.verify(cartItemMapper, Mockito.times(1)).toCartItem(cartItemDto);
        Mockito.verify(cartItemMapper, Mockito.times(1)).toCartItemDto(cartItem);
        Mockito.verify(cartItemRepository, Mockito.times(1)).save(cartItem);
    }

    @Test
    public void testAddToCart_NonExistingProduct_ShouldThrowNotFoundException() {
        // Arrange
        CartItemDto cartItemDto = new CartItemDto(3, 1L, 2L, null);

        Mockito.when(userRepository.existsById(cartItemDto.userId())).thenReturn(true);
        Mockito.when(productRepository.existsById(cartItemDto.productId())).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            cartItemService.addToCart(cartItemDto);
        });

        Mockito.verify(userRepository, Mockito.times(1)).existsById(cartItemDto.userId());
        Mockito.verify(productRepository, Mockito.times(1)).existsById(cartItemDto.productId());
        Mockito.verify(cartItemRepository, Mockito.never())
                .findByIdUserIdAndIdProductId(cartItemDto.userId(), cartItemDto.productId());
        Mockito.verify(cartItemRepository, Mockito.never())
                .findByIdUserIdAndIdProductId(cartItemDto.userId(), cartItemDto.productId());
        Mockito.verify(cartItemRepository, Mockito.never()).save(Mockito.any(CartItem.class));
    }

    @Test
    public void testAddToCart_ExistingProduct_ShouldAddToExistingCartItem() {
        // Arrange
        CartItemDto cartItemDto = new CartItemDto(3, 1L, 2L, null);

        User user = new User();
        user.setId(cartItemDto.userId());
        Product product = new Product();
        product.setId(cartItemDto.productId());
        CartItem existingCartItem = new CartItem();
        existingCartItem.setUser(user);
        existingCartItem.setProduct(product);
        existingCartItem.setQuantity(2);

        Mockito.when(userRepository.existsById(cartItemDto.userId())).thenReturn(true);
        Mockito.when(productRepository.existsById(cartItemDto.productId())).thenReturn(true);
        Mockito.when(cartItemRepository.findByIdUserIdAndIdProductId(cartItemDto.userId(), cartItemDto.productId()))
                .thenReturn(Optional.of(existingCartItem));
        Mockito.when(cartItemMapper.toCartItem(cartItemDto))
                .thenReturn(existingCartItem);

        // Act
        cartItemService.addToCart(cartItemDto);

        // Assert
        Mockito.verify(userRepository, Mockito.times(1)).existsById(cartItemDto.userId());
        Mockito.verify(productRepository, Mockito.times(1)).existsById(cartItemDto.productId());
        Mockito.verify(cartItemRepository, Mockito.times(1))
                .findByIdUserIdAndIdProductId(cartItemDto.userId(), cartItemDto.productId());
        Mockito.verify(cartItemRepository, Mockito.times(1)).save(existingCartItem);
        Assertions.assertEquals(5, existingCartItem.getQuantity());
    }
}