package pos.restaurant.DTO.DishCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pos.restaurant.DTO.Dish.DishDto;
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
    private Integer position;
    private List<DishDto> dishes;

    public static DishCategoryDto toDto(DishCategory dishCategory) {
        DishCategoryDto dishCategoryDto = new DishCategoryDto();
        dishCategoryDto.setId(dishCategory.getId());
        dishCategoryDto.setName(dishCategory.getName());
        dishCategoryDto.setPosition(dishCategory.getPosition());

        if (dishCategory.getDish() != null) {
            dishCategoryDto.setDishes(dishCategory.getDish().stream().map(DishDto::toDto).toList());
        }

        return dishCategoryDto;
    }

}
