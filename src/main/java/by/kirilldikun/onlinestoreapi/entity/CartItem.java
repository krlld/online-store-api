package by.kirilldikun.onlinestoreapi.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class CartItem {

    @EmbeddedId
    private UserProductId id;

    private Integer quantity;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("productId")
    private Product product;
}
