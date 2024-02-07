package by.kirilldikun.onlinestoreapi.repository;

import by.kirilldikun.onlinestoreapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    Optional<Product> findByName(String name);

    @Modifying
    @Query("UPDATE Product p SET p.isDeleted = true WHERE p.id = :id")
    int softDeleteById(Long id);
}
