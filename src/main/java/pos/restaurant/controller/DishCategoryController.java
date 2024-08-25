package pos.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pos.restaurant.DTO.DishCategoryDto;
import pos.restaurant.DTO.DishCategoryNameDto;
import pos.restaurant.models.Dish;
import pos.restaurant.models.DishCategory;
import pos.restaurant.records.ToggleIsDishEnable;
import pos.restaurant.service.DishCategoryService;

import java.lang.annotation.Documented;
import java.util.List;

@Controller
@RequestMapping("/api/dishCategory")
public class DishCategoryController {
    private final DishCategoryService dishCategoryService;

    public DishCategoryController(DishCategoryService dishCategoryService) {
        this.dishCategoryService = dishCategoryService;
    }

    @PostMapping()
    public ResponseEntity<DishCategory> createNewEmptyDishCategory(@RequestBody DishCategoryNameDto name) {
        DishCategory dishCategory = dishCategoryService.createNewEmptyDishCategory(name.getName());
        return ResponseEntity.ok(dishCategory);
    }

    @GetMapping("/toggleIsEnable/{id}")
    public ResponseEntity<DishCategoryDto> toggleIsEnable(@PathVariable Long id, @RequestBody ToggleIsDishEnable isEnabled) {
        DishCategoryDto dishCategory = dishCategoryService.toggleIsEnable(id, isEnabled.isEnabled());
        return ResponseEntity.ok(dishCategory);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DishCategoryDto>> getAllDishCategories() {
        return ResponseEntity.ok(dishCategoryService.getAllDishCategories());
    }

    @PostMapping("/addDish/{id}")
    public ResponseEntity<DishCategoryDto> addDish(@RequestBody Dish dish, @PathVariable Long id) {
        DishCategoryDto dishCategory = dishCategoryService.addDish(dish, id);
        return ResponseEntity.ok(dishCategory);
    }



    // utility endpoint to delete all dish categories
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll() {
        dishCategoryService.deleteAll();
        return ResponseEntity.ok("All dish categories deleted");
    }
}
