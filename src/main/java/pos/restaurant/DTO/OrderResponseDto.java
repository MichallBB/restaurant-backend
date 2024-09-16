package pos.restaurant.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pos.restaurant.models.Dish;
import pos.restaurant.models.OrderRestaurant;
import pos.restaurant.models.RestaurantTable;

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
    private List<DishWithCategoryDto> dish;

    private int price;
    private int quantity;
    private String specialRequest;
    private Date orderStartTime;
    private Date orderEndTime;

    private boolean isServed;
    private boolean isPaid;

    public static OrderResponseDto toDto(OrderRestaurant orderRestaurant) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(orderRestaurant.getId());
        orderResponseDto.setTableNumber(orderRestaurant.getRestaurantTable().getTableNumber());
        orderResponseDto.setWaiterId(orderRestaurant.getWaiter().getId());
        List<Dish> dishList = orderRestaurant.getDish();
        List<DishWithCategoryDto> dishWithCategoryDtoList =
            dishList.stream().map(DishWithCategoryDto::toDto).toList();
        orderResponseDto.setDish(dishWithCategoryDtoList);
        orderResponseDto.setPrice(orderRestaurant.getPrice());
        orderResponseDto.setQuantity(orderRestaurant.getQuantity());
        orderResponseDto.setSpecialRequest(orderRestaurant.getSpecialRequest());
        orderResponseDto.setOrderStartTime(orderRestaurant.getOrderStartTime());
        orderResponseDto.setOrderEndTime(orderRestaurant.getOrderEndTime());
        orderResponseDto.setServed(orderRestaurant.isServed());
        orderResponseDto.setPaid(orderRestaurant.isPaid());
        return orderResponseDto;
    }
}
