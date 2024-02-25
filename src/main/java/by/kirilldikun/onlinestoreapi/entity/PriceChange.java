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

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "price_change_history")
@Where(clause = "is_deleted = false")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class PriceChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    private BigDecimal priceBefore;

    @Column(insertable = false, updatable = false)
    private BigDecimal priceAfter;

    @Column(insertable = false, updatable = false)
    private ZonedDateTime changeData;

    @ManyToOne
    private Product product;

    @Column(insertable = false, updatable = false)
    private Boolean isDeleted;
}
