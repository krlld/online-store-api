package by.kirilldikun.onlinestoreapi.repository;

import by.kirilldikun.onlinestoreapi.dto.CategoryRevenueDto;
import by.kirilldikun.onlinestoreapi.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findAllByNameContainingIgnoreCase(String query, Pageable pageable);

    Optional<Category> findByName(String name);

    @Modifying
    @Query("UPDATE Category c SET c.isDeleted = true WHERE c.id = :id")
    int softDeleteById(Long id);

    @Query("""
            SELECT new by.kirilldikun.onlinestoreapi.dto.CategoryRevenueDto(name, SUM(total))
            FROM (SELECT c.name as name, o.price * o.quantity as total
            FROM Category c
            RIGHT JOIN Product p ON c.id = p.category.id
            RIGHT JOIN OrderItem o on p.id = o.product.id)
            GROUP BY name
            """)
    List<CategoryRevenueDto> getCategoriesTotalRevenue();
}
