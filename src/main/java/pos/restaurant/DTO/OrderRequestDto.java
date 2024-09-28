package pos.restaurant.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pos.restaurant.models.Dish;
import pos.restaurant.models.EmployeeAccount;
import pos.restaurant.models.OrderRestaurant;
import pos.restaurant.models.RestaurantTable;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private Long id;
    private RestaurantTable tableNumber;
    private Long waiterId;
    private List<DishDto> dish;

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
        orderRestaurant.setRestaurantTable(orderRequestDto.getTableNumber());
        orderRestaurant.setWaiter(waiter);
        List<Dish> dishList = orderRequestDto.getDish().stream().map(DishDto::toEntity).toList();
        orderRestaurant.setDish(dishList);
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
