package by.kirilldikun.onlinestoreapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Where(clause = "is_deleted = false")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String deliveryAddress;

    @ManyToOne
    private User user;

    @ManyToOne
    private OrderStatus orderStatus;

    @Column(insertable = false, updatable = false)
    private Boolean isDeleted;
}
