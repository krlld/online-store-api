package by.kirilldikun.onlinestoreapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Where(clause = "is_deleted = false")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    @Column(insertable = false, updatable = false)
    private BigDecimal averageRating;

    @Column(insertable = false, updatable = false)
    private Integer totalReviews;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "productId")
    private List<Image> images;

    @Column(insertable = false, updatable = false)
    private Boolean isDeleted;
}
