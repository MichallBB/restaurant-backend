package pos.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.restaurant.models.OrderRestaurant;
import pos.restaurant.models.RestaurantTable;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderRestaurant, Long> {
    List<OrderRestaurant> findAllByWaiterIdAndOrderEndedIsFalse(Long waiterId);
    List<OrderRestaurant> findAllByOrderEndedIsFalse();
    boolean existsByRestaurantTableId(Long tableId);
    List<OrderRestaurant> findAllByRestaurantTable(RestaurantTable table);
}