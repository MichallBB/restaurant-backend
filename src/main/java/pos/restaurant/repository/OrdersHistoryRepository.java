package pos.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.restaurant.models.history.OrdersHistory;

public interface OrdersHistoryRepository extends JpaRepository<OrdersHistory, Long> {
}