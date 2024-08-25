package pos.restaurant.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pos.restaurant.models.Dish;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishWithCategoryDto {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String dishCategoryName;
    private boolean isEnabled;

    public static DishWithCategoryDto toDto(Dish dish) {
        DishWithCategoryDto dishWithCategoryDto = new DishWithCategoryDto();
        dishWithCategoryDto.setId(dish.getId());
        dishWithCategoryDto.setName(dish.getName());
        dishWithCategoryDto.setPrice(dish.getPrice());
        dishWithCategoryDto.setDescription(dish.getDescription());
        dishWithCategoryDto.setDishCategoryName(dish.getDishCategory().getName());
        dishWithCategoryDto.setEnabled(dish.getIsEnabled());
        return dishWithCategoryDto;
    }
}
