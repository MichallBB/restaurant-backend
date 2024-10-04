package pos.restaurant.service;

import org.springframework.stereotype.Service;
import pos.restaurant.DTO.Dish.DishDto;
import pos.restaurant.DTO.Dish.DishWithCategoryNameDto;
import pos.restaurant.models.Dish;
import pos.restaurant.records.ToggleIsDishEnable;
import pos.restaurant.repository.DishRepository;
import pos.restaurant.utils.DishCategoryNameComparator;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public void removeDish(Long id) {
        dishRepository.deleteById(id);
    }

    public List<DishWithCategoryNameDto> getAllDishes() {
        List<Dish> dish = dishRepository.findAll();
        dish.sort(new DishCategoryNameComparator());
        List<DishWithCategoryNameDto> dishWithCategoryNameDtos = new ArrayList<>();
        for (Dish d : dish) {
            dishWithCategoryNameDtos.add(DishWithCategoryNameDto.toDto(d));
        }

        return dishWithCategoryNameDtos;
    }

    public DishDto toggleIsEnable(Long id, ToggleIsDishEnable isEnabled) {
        Dish dish = dishRepository.findById(id).orElseThrow();
        dish.setEnabled(isEnabled.isEnabled());
        Dish updatedDish = dishRepository.save(dish);
        return DishDto.toDto(updatedDish);
    }

    public void editDish(DishDto dishDto) {

    }
}
