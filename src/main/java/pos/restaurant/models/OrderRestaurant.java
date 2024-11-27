package pos.restaurant.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
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

    @OneToMany(mappedBy = "orderRestaurant", cascade = CascadeType.ALL)
    private List<DishInOrder> dishes;

    private boolean orderEnded;


    private int price;
    private int quantity;
    private String specialRequest;
    private Date orderStartTime;
    private Date orderEndTime;


}
