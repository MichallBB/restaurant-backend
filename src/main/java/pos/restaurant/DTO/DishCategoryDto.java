package pos.restaurant.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pos.restaurant.models.Dish;
import pos.restaurant.models.DishCategory;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishCategoryDto {
    private Long id;
    private String name;
    private boolean isEnabled;
    private Integer position;
    private List<DishDto> dishes;

    public static DishCategoryDto toDto(DishCategory dishCategory) {
        DishCategoryDto dishCategoryDto = new DishCategoryDto();
        dishCategoryDto.setId(dishCategory.getId());
        dishCategoryDto.setName(dishCategory.getName());
        dishCategoryDto.setEnabled(dishCategory.getIsEnabled());
        dishCategoryDto.setPosition(dishCategory.getPosition());

        List<Dish> dishes = dishCategory.getDish();
        List<DishDto> dishDtos = new ArrayList<>();
        for (Dish dish : dishes) {
            dishDtos.add(DishDto.toDto(dish));
        }

        dishCategoryDto.setDishes(dishDtos);
        return dishCategoryDto;
    }

}
