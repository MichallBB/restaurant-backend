package pos.restaurant.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.restaurant.DTO.RestaurantTableDto.RestaurantTableDto;
import pos.restaurant.DTO.tableAvailable;
import pos.restaurant.models.RestaurantTable;
import pos.restaurant.service.RestaurantTableService;
import pos.restaurant.utils.ApiResponse;

import javax.xml.crypto.Data;
import java.util.List;

@RestController
@RequestMapping("/api/table")
public class RestaurantTableController {
    private final RestaurantTableService restaurantTableService;

    public RestaurantTableController(RestaurantTableService restaurantTableService) {
        this.restaurantTableService = restaurantTableService;
    }

    @PostMapping("/createTable")
    public ResponseEntity<RestaurantTableDto> createTable(@RequestBody RestaurantTable restaurantTable) {
        try {
            return ResponseEntity.ok(restaurantTableService.createTable(restaurantTable));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(414).build();
        }
    }

    @GetMapping("/checkTable/{tableNumber}")
    public ResponseEntity<tableAvailable> checkTable(@PathVariable String tableNumber) {
        return ResponseEntity.ok(restaurantTableService.checkTable(tableNumber));
    }

    @GetMapping("/getAllTables")
    public ResponseEntity<List<RestaurantTableDto>> getAllTables() {
        return ResponseEntity.ok(restaurantTableService.getAllTables());
    }

    @GetMapping("/editTable/{id}/{tableNumber}")
    public ResponseEntity<RestaurantTableDto> editTable(@PathVariable String tableNumber,@PathVariable Long id) {
        try {
            return ResponseEntity.ok(restaurantTableService.editTable(tableNumber, id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/deleteTable/{tableNumber}")
    public ResponseEntity<ApiResponse> deleteTable(@PathVariable String tableNumber) {
        try {
            restaurantTableService.deleteTable(tableNumber);
            return ResponseEntity.ok().body(new ApiResponse("Table deleted"));
        } catch (DataIntegrityViolationException e) {
            // 414 status code is for table with existing orders
            return ResponseEntity.status(414).build();
        } catch (IllegalArgumentException e) {
            // 404 status code is for table not found
            return ResponseEntity.status(404).build();
        }
    }
}
