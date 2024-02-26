package by.kirilldikun.onlinestoreapi.repository;

import by.kirilldikun.onlinestoreapi.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByIdUserId(Long userId);

    Page<CartItem> findAllByIdUserId(Long userId, Pageable pageable);

    Optional<CartItem> findByIdUserIdAndIdProductId(Long userId, Long productId);

    int deleteByIdUserIdAndIdProductId(Long userId, Long productId);

    int deleteAllByIdUserId(Long userId);
}
