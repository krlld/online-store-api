package by.kirilldikun.onlinestoreapi.repository;

import by.kirilldikun.onlinestoreapi.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT p.id
            FROM Product p INNER JOIN CartItem c ON p.id = c.id.productId
            WHERE c.id.userId = :userId AND (p.name LIKE %:query% or p.description LIKE %:query%)
            """)
    List<Long> findAllProductIdsThatInUserCart(Long userId, String query, Pageable pageable);

    @Query("""
            SELECT p.id
            FROM Product p INNER JOIN FavoriteItem f ON p.id = f.id.productId
            WHERE f.id.userId = :userId AND (p.name LIKE %:query% or p.description LIKE %:query%)
            """)
    List<Long> findAllProductIdsThatInUserFavorite(Long userId, String query, Pageable pageable);
}
