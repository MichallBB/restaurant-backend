package pos.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pos.restaurant.DTO.Dish.DishDto;
import pos.restaurant.DTO.Dish.DishWithCategoryNameDto;
import pos.restaurant.records.ToggleIsDishEnable;
import pos.restaurant.service.DishService;
import pos.restaurant.utils.ApiResponse;

import java.util.List;

@Controller
@RequestMapping("/api/dish")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @DeleteMapping("/removeDish/{id}")
    public ResponseEntity<ApiResponse> removeDish(@PathVariable Long id) {
        try {
            dishService.removeDish(id);
            return ResponseEntity.ok(new ApiResponse("Dish removed"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Dish not found"));
        }
    }

    @GetMapping("/getAllDishes")
    public ResponseEntity<List<DishWithCategoryNameDto>>  getAllDishes() {
        return ResponseEntity.ok(dishService.getAllDishes());
    }

    @PostMapping("/toggleIsEnable/{id}")
    public ResponseEntity<DishDto> toggleIsEnable(@PathVariable Long id, @RequestBody ToggleIsDishEnable isEnabled) {
        DishDto dish = dishService.toggleIsEnable(id, isEnabled);

        return ResponseEntity.ok(dish);
    }

    @PostMapping("/editDish")
    public ResponseEntity<ApiResponse> editDish(@RequestBody DishWithCategoryNameDto dishDto) {
        try {
            dishService.editDish(dishDto);
            return ResponseEntity.ok(new ApiResponse("Dish edited"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Dish not found"));
        }
    }
}
