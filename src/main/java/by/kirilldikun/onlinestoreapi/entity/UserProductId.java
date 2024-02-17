package by.kirilldikun.onlinestoreapi.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class UserProductId implements Serializable {

    private Long userId;

    private Long productId;
}
