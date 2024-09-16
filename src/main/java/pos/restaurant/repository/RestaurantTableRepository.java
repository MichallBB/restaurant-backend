package pos.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.restaurant.models.RestaurantTable;

import java.util.List;
import java.util.Optional;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    Optional<List<RestaurantTable>> findByTableNumber(String tableNumber);
}