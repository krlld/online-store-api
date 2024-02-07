package by.kirilldikun.onlinestoreapi.repository;

import by.kirilldikun.onlinestoreapi.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Modifying
    @Query("UPDATE Image i SET i.isDeleted = true WHERE i.id = :productId")
    void softDeleteAllByProductId(Long productId);
}
