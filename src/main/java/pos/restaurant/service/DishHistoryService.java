package pos.restaurant.service;

import org.springframework.stereotype.Service;
import pos.restaurant.models.Dish;
import pos.restaurant.models.DishInOrder;
import pos.restaurant.models.history.DishInOrderHistory;
import pos.restaurant.repository.DishInOrderHistoryRepository;
import pos.restaurant.repository.DishInOrderRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DishHistoryService {
    private final DishInOrderHistoryRepository dishInOrderRepository;

    public DishHistoryService(DishInOrderHistoryRepository dishInOrderRepository) {
        this.dishInOrderRepository = dishInOrderRepository;
    }

    public void createDishHistory(List<DishInOrder> dishes) {
        for (DishInOrder dish : dishes) {
            if(dishInOrderRepository.existsByDishName(dish.getDish().getName())){
                DishInOrderHistory dishInOrderHistory1 = dishInOrderRepository.findByDishName(dish.getDish().getName());
                dishInOrderHistory1.setQuantity(dishInOrderHistory1.getQuantity() + 1);
                dishInOrderHistory1.setOrderStartTime(new Date());
                dishInOrderRepository.save(dishInOrderHistory1);
            }
            else{
                DishInOrderHistory dishInOrderHistory = new DishInOrderHistory();
                dishInOrderHistory.setDishName(dish.getDish().getName());
                dishInOrderHistory.setQuantity(1);
                dishInOrderHistory.setOrderStartTime(new Date());
                dishInOrderRepository.save(dishInOrderHistory);
            }
        }
    }

    public List<DishInOrderHistory> getAllFromLastWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date lastWeek = calendar.getTime();
        return dishInOrderRepository.findAllByOrderStartTimeAfter(lastWeek);
    }

    public void deleteHistory() {
        dishInOrderRepository.deleteAll();
    }
}
