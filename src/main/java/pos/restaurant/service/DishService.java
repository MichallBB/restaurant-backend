package pos.restaurant.service;

import org.springframework.stereotype.Service;
import pos.restaurant.DTO.DishDto;
import pos.restaurant.DTO.DishWithCategoryDto;
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

    public List<DishWithCategoryDto> getAllDishes() {
        List<Dish> dish = dishRepository.findAll();
        dish.sort(new DishCategoryNameComparator());
        List<DishWithCategoryDto> dishWithCategoryDtos = new ArrayList<>();
        for (Dish d : dish) {
            dishWithCategoryDtos.add(DishWithCategoryDto.toDto(d));
        }

        return dishWithCategoryDtos;
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
