package pos.restaurant.DTO.Dish;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pos.restaurant.models.Dish;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishWithCategoryNameDto {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String dishCategoryName;
    private boolean isEnabled;

    public static DishWithCategoryNameDto toDto(Dish dish) {
        DishWithCategoryNameDto dishWithCategoryNameDto = new DishWithCategoryNameDto();
        dishWithCategoryNameDto.setId(dish.getId());
        dishWithCategoryNameDto.setName(dish.getName());
        dishWithCategoryNameDto.setPrice(dish.getPrice());
        dishWithCategoryNameDto.setDescription(dish.getDescription());
        dishWithCategoryNameDto.setDishCategoryName(dish.getDishCategory().getName());
        dishWithCategoryNameDto.setEnabled(dish.getIsEnabled());
        return dishWithCategoryNameDto;
    }
}
