package pos.restaurant.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pos.restaurant.DTO.Order.DishInOrderDto;
import pos.restaurant.models.DishInOrder;
import pos.restaurant.repository.DishInOrderRepository;

import java.util.List;

@Service
public class DishInOrderService {
    private final DishInOrderRepository dishInOrderRepository;

    public DishInOrderService(DishInOrderRepository dishInOrderRepository) {
        this.dishInOrderRepository = dishInOrderRepository;
    }

    public List<DishInOrderDto> getall() {
        List<DishInOrder> dish = dishInOrderRepository.findAll();
        return dish.stream().map(DishInOrderDto::toDto).toList();
    }

    public DishInOrderDto toggleCooked(Long id, boolean cooked) {
        DishInOrder dish = dishInOrderRepository.findById(id).orElseThrow();
        dish.setCooked(cooked);
        dishInOrderRepository.save(dish);
        return DishInOrderDto.toDto(dish);
    }

    public DishInOrderDto toggleServed(Long id, boolean served) {
        DishInOrder dish = dishInOrderRepository.findById(id).orElseThrow();
        dish.setServed(served);
        dishInOrderRepository.save(dish);
        return DishInOrderDto.toDto(dish);
    }
}
