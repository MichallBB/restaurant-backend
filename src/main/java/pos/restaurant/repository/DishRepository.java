package pos.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.restaurant.models.Dish;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
}