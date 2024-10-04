package pos.restaurant.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DishInOrder> dishInOrder;

    public boolean getIsEnabled() {
        return isEnabled;
    }

}
