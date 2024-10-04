package pos.restaurant.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private Integer position;

    @OneToMany(mappedBy = "dishCategory", cascade = CascadeType.ALL)
    private List<Dish> dish;

}
