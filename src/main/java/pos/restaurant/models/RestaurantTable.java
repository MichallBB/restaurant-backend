package pos.restaurant.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String tableNumber;
    private boolean isOccupied;
    private boolean isReserved;
    private Date reservationTime;


    @OneToMany(mappedBy = "restaurantTable")
    private List<OrderRestaurant> orders;




}
