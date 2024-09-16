package pos.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pos.restaurant.DTO.RestaurantTableDto.RestaurantTableDto;
import pos.restaurant.models.RestaurantTable;
import pos.restaurant.service.RestaurantTableService;

import java.util.List;

@Controller
@RequestMapping("/api/table")
public class RestaurantTableController {
    private final RestaurantTableService restaurantTableService;

    public RestaurantTableController(RestaurantTableService restaurantTableService) {
        this.restaurantTableService = restaurantTableService;
    }

    @PostMapping("/createTable")
    public ResponseEntity<RestaurantTableDto> createTable(@RequestBody RestaurantTable restaurantTable) {
        return ResponseEntity.ok(restaurantTableService.createTable(restaurantTable));
    }

    @GetMapping("/getAllTables")
    public ResponseEntity<List<RestaurantTableDto>> getAllTables() {
        return ResponseEntity.ok(restaurantTableService.getAllTables());
    }
}
