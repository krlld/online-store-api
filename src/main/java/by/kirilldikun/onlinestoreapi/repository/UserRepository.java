package by.kirilldikun.onlinestoreapi.repository;

import by.kirilldikun.onlinestoreapi.dto.UserExpensesDto;
import by.kirilldikun.onlinestoreapi.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("""
            SELECT new by.kirilldikun.onlinestoreapi.dto.UserExpensesDto(name, SUM(total))
            FROM (SELECT u.name as name, oi.price * oi.quantity as total
            FROM User u
            RIGHT JOIN Order o ON u.id = o.user.id
            RIGHT JOIN OrderItem oi on o.id = oi.order.id)
            GROUP BY name
            ORDER BY SUM(total) DESC
            """)
    List<UserExpensesDto> getUsersExpenses(Pageable pageable);
}
