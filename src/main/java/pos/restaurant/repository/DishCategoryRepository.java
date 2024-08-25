package pos.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.restaurant.models.DishCategory;

import java.util.List;

public interface DishCategoryRepository extends JpaRepository<DishCategory, Long> {
    List<DishCategory> findAllByOrderByPositionAsc();
}