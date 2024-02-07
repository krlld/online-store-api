package by.kirilldikun.onlinestoreapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String description;

    private BigDecimal averageRating;

    private Integer totalReviews;

    @NotNull
    private List<ImageDto> images;

    @NotNull
    private Long categoryId;
}
