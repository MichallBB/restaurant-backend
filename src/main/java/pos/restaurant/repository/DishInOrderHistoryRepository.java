package pos.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.restaurant.models.history.DishInOrderHistory;

import java.util.Date;
import java.util.List;

public interface DishInOrderHistoryRepository extends JpaRepository<DishInOrderHistory, Long> {
    boolean existsByDishName(String dishName);
    DishInOrderHistory findByDishName(String dishName);

    List<DishInOrderHistory> findAllByOrderStartTimeAfter(Date date);
}