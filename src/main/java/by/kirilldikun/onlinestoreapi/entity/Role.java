package by.kirilldikun.onlinestoreapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "roles")
@Where(clause = "is_deleted = false")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class Role {

    @Id
    private String name;

    @Column(insertable = false, updatable = false)
    private Boolean isDeleted;
}
