package pos.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.restaurant.models.DishInOrder;

public interface DishInOrderRepository extends JpaRepository<DishInOrder, Long> {
}