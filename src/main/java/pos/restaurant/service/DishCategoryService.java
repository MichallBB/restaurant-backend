package pos.restaurant.service;

import org.springframework.stereotype.Service;
import pos.restaurant.DTO.DishCategoryDto;
import pos.restaurant.Mapper.DishCategoryMapper;
import pos.restaurant.models.Dish;
import pos.restaurant.models.DishCategory;
import pos.restaurant.repository.DishCategoryRepository;
import pos.restaurant.repository.DishRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishCategoryService {
    private final DishCategoryRepository dishCategoryRepository;
    private DishCategoryMapper dishCategoryMapper;
    private final DishRepository dishRepository;

    public DishCategoryService(
            DishCategoryRepository dishCategoryRepository,
            DishCategoryMapper dishCategoryMapper,
            DishRepository dishRepository
    ) {
        this.dishCategoryRepository = dishCategoryRepository;
        this.dishCategoryMapper = dishCategoryMapper;
        this.dishRepository = dishRepository;
    }

    public DishCategory createNewEmptyDishCategory(String name) {
        DishCategory dishCategory = new DishCategory();
        dishCategory.setName(name);
        dishCategory.setEnabled(true);
        int position = dishCategoryRepository.findAll().size();

        if (position == 0) {
            dishCategory.setPosition(0);
        }else {
            dishCategory.setPosition(position + 1);
        }
        DishCategory savedDishCategory = dishCategoryRepository.save(dishCategory);
        return savedDishCategory;
    }

    public DishCategoryDto toggleIsEnable(Long id, boolean isEnabled) {
        DishCategory dishCategory = dishCategoryRepository.findById(id).orElseThrow();
        dishCategory.setEnabled(isEnabled);
        DishCategory updatedDishCategory = dishCategoryRepository.save(dishCategory);
        return DishCategoryDto.toDto(updatedDishCategory);
    }


    public List<DishCategoryDto> getAllDishCategories() {
        List<DishCategory> dishCategories = dishCategoryRepository.findAllByOrderByPositionAsc();
        List<DishCategoryDto> dishCategoryDtos = new ArrayList<>();
        for (DishCategory dishCategory : dishCategories) {
            dishCategoryDtos.add(DishCategoryDto.toDto(dishCategory));
        }
        return dishCategoryDtos;
    }

    public DishCategoryDto addDish(Dish dish, Long id) {
        DishCategory dishCategory = dishCategoryRepository.findById(id).orElseThrow();

        Dish persistDish = new Dish();
        persistDish.setName(dish.getName());
        persistDish.setPrice(dish.getPrice());
        persistDish.setDescription(dish.getDescription());
        persistDish.setDishCategory(dishCategory);

        List<Dish> dishes = dishCategory.getDish();
        dishes.add(persistDish);
        dishCategory.setDish(dishes);
        DishCategory updatedDishCategory = dishCategoryRepository.save(dishCategory);

        return DishCategoryDto.toDto(updatedDishCategory);
    }



    public void deleteAll() {
        dishCategoryRepository.deleteAll();
    }


}
