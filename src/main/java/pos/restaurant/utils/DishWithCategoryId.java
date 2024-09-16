package pos.restaurant.utils;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pos.restaurant.models.DishCategory;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DishWithCategoryId {
    private String name;
    private double price;
    private String description;
    private Long dishCategoryId;
}
