package pos.restaurant.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private RestaurantTable restaurantTable;
    @ManyToOne
    private EmployeeAccount waiter;

    @ManyToMany
    private List<Dish> dish;

    private int price;
    private int quantity;
    private String specialRequest;
    private Date orderStartTime;
    private Date orderEndTime;

    private boolean isServed;
    private boolean isPaid;


}
