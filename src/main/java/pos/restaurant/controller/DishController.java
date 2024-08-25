package pos.restaurant.controller;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pos.restaurant.DTO.DishDto;
import pos.restaurant.DTO.DishWithCategoryDto;
import pos.restaurant.models.Dish;
import pos.restaurant.records.ToggleIsDishEnable;
import pos.restaurant.service.DishService;

import java.util.List;

@Controller
@RequestMapping("/api/dish")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @DeleteMapping("/removeDish/{id}")
    public void removeDish(@PathVariable Long id) {
        dishService.removeDish(id);
    }

    @GetMapping("/getAllDishes")
    public ResponseEntity<List<DishWithCategoryDto>>  getAllDishes() {
        return ResponseEntity.ok(dishService.getAllDishes());
    }

    @PostMapping("/toggleIsEnable/{id}")
    public ResponseEntity<DishDto> toggleIsEnable(@PathVariable Long id, @RequestBody ToggleIsDishEnable isEnabled) {
        DishDto dish = dishService.toggleIsEnable(id, isEnabled);

        return ResponseEntity.ok(dish);
    }
}
