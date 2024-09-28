package pos.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pos.restaurant.DTO.DishCategoryDto;
import pos.restaurant.DTO.DishCategoryNameDto;
import pos.restaurant.exceptions.DishCategoryNotFound;
import pos.restaurant.models.Dish;
import pos.restaurant.models.DishCategory;
import pos.restaurant.records.ToggleIsDishEnable;
import pos.restaurant.service.DishCategoryService;
import pos.restaurant.utils.ApiResponse;
import pos.restaurant.utils.DishWithCategoryId;

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
    public ResponseEntity<DishCategoryDto> createNewEmptyDishCategory(@RequestBody DishCategoryNameDto name) {
        try {
        DishCategoryDto dishCategory = dishCategoryService.createNewEmptyDishCategory(name.getName());
        return ResponseEntity.ok(dishCategory);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(415).body(null);
        }
    }

    @PostMapping("/editName/{id}")
    public ResponseEntity<DishCategoryDto> editName(@RequestBody DishCategoryNameDto name, @PathVariable Long id) {
        try{
            DishCategoryDto dishCategoryDto = dishCategoryService.editName(name.getName(), id);
            return ResponseEntity.ok(dishCategoryDto);
        }catch (DishCategoryNotFound e){
            return ResponseEntity.status(415).body(null);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DishCategoryDto>> getAllDishCategories() {
        return ResponseEntity.ok(dishCategoryService.getAllDishCategories());
    }

    @GetMapping("/getAllEnabled")
    public ResponseEntity<List<DishCategoryDto>> getAllEnabledDishCategories() {
        return ResponseEntity.ok(dishCategoryService.getAllEnabledDishCategories());
    }

    @PostMapping("/addDish")
    public ResponseEntity<DishCategoryDto> addDish(@RequestBody DishWithCategoryId dish) {
        DishCategoryDto dishCategory = dishCategoryService.addDish(dish);
        return ResponseEntity.ok(dishCategory);
    }

    @PostMapping("/moveDishCategory/{id}/{newIndex}")
    public ResponseEntity<DishCategoryDto> moveDishCategory(@PathVariable Long id, @PathVariable Integer newIndex) {
        try {
            return ResponseEntity.ok().body(dishCategoryService.moveDishCategory(id, newIndex));
        }catch (DishCategoryNotFound e){
            return ResponseEntity.status(415).body(null);
        }
    }

    @DeleteMapping("/removeDishCategory/{id}")
    public ResponseEntity<ApiResponse> removeDishCategory(@PathVariable Long id) {
        try {
            dishCategoryService.removeDishCategory(id);
            return ResponseEntity.ok(new ApiResponse("Dish category removed"));
        }catch (DishCategoryNotFound e){
            return ResponseEntity.status(415).body(null);
        }
    }



    // utility endpoint to delete all dish categories

    @DeleteMapping("/deleteAll")
    public ResponseEntity<ApiResponse> deleteAll() {
        dishCategoryService.deleteAll();
        return ResponseEntity.ok(new ApiResponse("removed"));
    }
}
