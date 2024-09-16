package pos.restaurant.service;

import org.springframework.stereotype.Service;
import pos.restaurant.DTO.DishCategoryDto;
import pos.restaurant.Mapper.DishCategoryMapper;
import pos.restaurant.exceptions.DishCategoryNotFound;
import pos.restaurant.models.Dish;
import pos.restaurant.models.DishCategory;
import pos.restaurant.repository.DishCategoryRepository;
import pos.restaurant.repository.DishRepository;
import pos.restaurant.utils.DishWithCategoryId;

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

    public DishCategoryDto createNewEmptyDishCategory(String name) {
        DishCategory dishCategory = new DishCategory();
        dishCategory.setName(name);
        // white space check
        if (name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        int position = dishCategoryRepository.findAll().size();

        if (position == 0) {
            dishCategory.setPosition(0);
        }else {
            dishCategory.setPosition(position + 1);
        }
        DishCategory savedDishCategory = dishCategoryRepository.save(dishCategory);
        return DishCategoryDto.toDto(savedDishCategory);
    }


    public List<DishCategoryDto> getAllDishCategories() {
        List<DishCategory> dishCategories = dishCategoryRepository.findAllByOrderByPositionAsc();
        List<DishCategoryDto> dishCategoryDtos = new ArrayList<>();
        for (DishCategory dishCategory : dishCategories) {
            dishCategoryDtos.add(DishCategoryDto.toDto(dishCategory));
        }
        return dishCategoryDtos;
    }

    public DishCategoryDto addDish(DishWithCategoryId dish) {
        DishCategory dishCategory = dishCategoryRepository.findById(dish.getDishCategoryId()).orElseThrow();

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

    public DishCategoryDto moveDishCategory(Long id, Integer newIndex){
        DishCategory dishCategory = dishCategoryRepository.findById(id).orElseThrow(
                () -> new DishCategoryNotFound("Dish category not found")
        );
        List<DishCategory> dishCategories = dishCategoryRepository.findAllByOrderByPositionAsc();
        dishCategories.remove(dishCategory);
        dishCategories.add(newIndex, dishCategory);
        for (int i = 0; i < dishCategories.size(); i++) {
            dishCategories.get(i).setPosition(i);
        }
        dishCategoryRepository.saveAll(dishCategories);

        dishCategory = dishCategoryRepository.findById(id).orElseThrow();
        return DishCategoryDto.toDto(dishCategory);
    }

    public DishCategoryDto editName(String name, Long id) {
        DishCategory dishCategory = dishCategoryRepository.findById(id).orElseThrow(
                () -> new DishCategoryNotFound("Dish category not found")
        );
        dishCategory.setName(name);
        DishCategory updatedDishCategory = dishCategoryRepository.save(dishCategory);
        return DishCategoryDto.toDto(updatedDishCategory);
    }

    public void removeDishCategory(Long id) {
        DishCategory dishCategory = dishCategoryRepository.findById(id).orElseThrow(
                () -> new DishCategoryNotFound("Dish category not found")
        );
        dishCategoryRepository.delete(dishCategory);
    }



    public void deleteAll() {
        dishCategoryRepository.deleteAll();
    }


}
