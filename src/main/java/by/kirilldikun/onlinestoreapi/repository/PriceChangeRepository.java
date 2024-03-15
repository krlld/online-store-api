package by.kirilldikun.onlinestoreapi.repository;

import by.kirilldikun.onlinestoreapi.entity.PriceChange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceChangeRepository extends JpaRepository<PriceChange, Long> {

    List<PriceChange> findAllByProductId(Long productId);
}
