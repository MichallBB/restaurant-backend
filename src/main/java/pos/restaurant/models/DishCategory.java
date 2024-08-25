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
@ToString
public class DishCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    @Setter
    @Column(name = "is_enabled_column")
    private boolean isEnabled=true;
    private Integer position;

    @OneToMany(mappedBy = "dishCategory", cascade = CascadeType.ALL)
    private List<Dish> dish;


    public boolean getIsEnabled() {
        return isEnabled;
    }
}
