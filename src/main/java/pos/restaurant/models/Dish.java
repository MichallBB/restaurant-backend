package pos.restaurant.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private double price;
    private String description;
    private boolean isEnabled = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_category_id")
    private DishCategory dishCategory;

    public boolean getIsEnabled() {
        return isEnabled;
    }

}
