package pos.restaurant.DTO.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pos.restaurant.DTO.Dish.DishDto;
import pos.restaurant.models.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private Long id;
    private RestaurantTable table;
    private Long waiterId;
    private List<DishInOrderDto> dishes;

    private int price;
    private int quantity;
    private String specialRequest;
    private Date orderStartTime;
    private Date orderEndTime;

    private boolean isServed;
    private boolean isPaid;


    public static OrderRestaurant toEntity(OrderRequestDto orderRequestDto, EmployeeAccount waiter) {
        OrderRestaurant orderRestaurant = new OrderRestaurant();
        orderRestaurant.setId(orderRequestDto.getId());
        orderRestaurant.setRestaurantTable(orderRequestDto.getTable());
        orderRestaurant.setWaiter(waiter);

        if (orderRequestDto.getDishes() != null) {
            List<DishInOrder> dishList = orderRequestDto.getDishes()
                    .stream().map(DishInOrderDto::toEntity).toList();
            orderRestaurant.setDishes(dishList);
        }

        orderRestaurant.setPrice(orderRequestDto.getPrice());
        orderRestaurant.setQuantity(orderRequestDto.getQuantity());
        orderRestaurant.setSpecialRequest(orderRequestDto.getSpecialRequest());
        orderRestaurant.setOrderStartTime(orderRequestDto.getOrderStartTime());
        orderRestaurant.setOrderEndTime(orderRequestDto.getOrderEndTime());
        orderRestaurant.setServed(orderRequestDto.isServed());
        orderRestaurant.setPaid(orderRequestDto.isPaid());
        return orderRestaurant;
    }
}
