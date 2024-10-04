package pos.restaurant.DTO.Dish;

import pos.restaurant.models.Dish;

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
    private boolean enabled;


    public boolean getIsEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static DishDto toDto(Dish dish) {
        DishDto dishDto = new DishDto();
        dishDto.setId(dish.getId());
        dishDto.setName(dish.getName());
        dishDto.setPrice(dish.getPrice());
        dishDto.setDescription(dish.getDescription());
        dishDto.setEnabled(dish.getIsEnabled());
        return dishDto;
    }

    public static Dish toEntity(DishDto dishDto) {
        Dish dish = new Dish();
        dish.setId(dishDto.getId());
        dish.setName(dishDto.getName());
        dish.setPrice(dishDto.getPrice());
        dish.setDescription(dishDto.getDescription());
        dish.setEnabled(dishDto.getIsEnabled());
        return dish;
    }
}
