package pos.restaurant.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishInOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private boolean served;
    private boolean cooked;

    @ManyToOne
    private Dish dish;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private OrderRestaurant orderRestaurant;
}
