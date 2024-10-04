package pos.restaurant.DTO.Order;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pos.restaurant.DTO.Dish.DishDto;
import pos.restaurant.models.Dish;
import pos.restaurant.models.DishInOrder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishInOrderDto {
    private Long id;
    private boolean served;
    private boolean cooked;
    private DishDto dish;

    public static DishInOrderDto toDto(DishInOrder dishInOrder) {
        DishInOrderDto dishInOrderDto = new DishInOrderDto();
        dishInOrderDto.setId(dishInOrder.getId());
        dishInOrderDto.setServed(dishInOrder.isServed());
        dishInOrderDto.setCooked(dishInOrder.isCooked());

        if (dishInOrder.getDish() != null) {
            dishInOrderDto.setDish(DishDto.toDto(dishInOrder.getDish()));
        }
        return dishInOrderDto;
    }

    public static DishInOrder toEntity(DishInOrderDto dishInOrderDto) {
        DishInOrder dishInOrder = new DishInOrder();
        dishInOrder.setId(dishInOrderDto.getId());
        dishInOrder.setServed(dishInOrderDto.isServed());
        dishInOrder.setCooked(dishInOrderDto.isCooked());

        if (dishInOrderDto.getDish() != null) {
            dishInOrder.setDish(DishDto.toEntity(dishInOrderDto.getDish()));
        }
        return dishInOrder;
    }
}
