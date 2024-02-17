package by.kirilldikun.onlinestoreapi.repository;

import by.kirilldikun.onlinestoreapi.entity.FavoriteItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteItemRepository extends JpaRepository<FavoriteItem, Long> {

    Page<FavoriteItem> findAllByIdUserId(Long userId, Pageable pageable);

    int deleteByIdUserIdAndIdProductId(Long userId, Long productId);
}
