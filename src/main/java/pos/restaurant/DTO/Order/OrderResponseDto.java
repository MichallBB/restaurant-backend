package pos.restaurant.DTO.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pos.restaurant.DTO.Dish.DishWithCategoryNameDto;
import pos.restaurant.models.Dish;
import pos.restaurant.models.DishInOrder;
import pos.restaurant.models.OrderRestaurant;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private String tableNumber;
    private Long waiterId;
    private List<DishInOrderDto> dishes;

    private int price;
    private int quantity;
    private String specialRequest;
    private Date orderStartTime;
    private Date orderEndTime;
    private boolean orderEnded;

    public static OrderResponseDto toDto(OrderRestaurant orderRestaurant) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(orderRestaurant.getId());

        if (orderRestaurant.getRestaurantTable() != null) {
            orderResponseDto.setTableNumber(orderRestaurant.getRestaurantTable().getTableNumber());
        }

        if (orderRestaurant.getWaiter() != null){
            orderResponseDto.setWaiterId(orderRestaurant.getWaiter().getId());
        }

        if (orderRestaurant.getDishes() != null) {
            List<DishInOrderDto> dishes =
                    orderRestaurant.getDishes().stream().map(DishInOrderDto::toDto).toList();
            orderResponseDto.setDishes(dishes);
        }

        orderResponseDto.setPrice(orderRestaurant.getPrice());
        orderResponseDto.setQuantity(orderRestaurant.getQuantity());
        orderResponseDto.setSpecialRequest(orderRestaurant.getSpecialRequest());
        orderResponseDto.setOrderStartTime(orderRestaurant.getOrderStartTime());
        orderResponseDto.setOrderEndTime(orderRestaurant.getOrderEndTime());
        orderResponseDto.setOrderEnded(orderRestaurant.isOrderEnded());
        return orderResponseDto;
    }
}
