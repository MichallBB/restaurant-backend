package pos.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.restaurant.models.OrderRestaurant;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderRestaurant, Long> {
    List<OrderRestaurant> findAllByWaiterId(Long waiterId);
}