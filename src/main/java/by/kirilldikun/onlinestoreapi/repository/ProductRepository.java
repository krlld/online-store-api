package by.kirilldikun.onlinestoreapi.repository;

import by.kirilldikun.onlinestoreapi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR
            LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))
             """)
    Page<Product> findAllByQuery(String query, Pageable pageable);

    Optional<Product> findByName(String name);

    @Modifying
    @Query("UPDATE Product p SET p.isDeleted = true WHERE p.id = :id")
    int softDeleteById(Long id);
}
