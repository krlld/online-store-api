package by.kirilldikun.onlinestoreapi.repository;

import by.kirilldikun.onlinestoreapi.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByProductId(Long productId, Pageable pageable);

    @Modifying
    @Query("UPDATE Review r SET r.isDeleted = true WHERE r.id = :id")
    int softDeleteById(Long id);
}
