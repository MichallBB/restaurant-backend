package pos.restaurant.DTO;

import jakarta.persistence.*;
import pos.restaurant.models.Dish;
import pos.restaurant.models.DishCategory;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {
    private Long id;
    private String name;
    private double price;
    private String description;
    private boolean Enabled;

    public static DishDto toDto(Dish dish) {
        DishDto dishDto = new DishDto();
        dishDto.setId(dish.getId());
        dishDto.setName(dish.getName());
        dishDto.setPrice(dish.getPrice());
        dishDto.setDescription(dish.getDescription());
        dishDto.setEnabled(dish.getIsEnabled());
        return dishDto;
    }
}
