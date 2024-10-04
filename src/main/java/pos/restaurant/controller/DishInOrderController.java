package pos.restaurant.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pos.restaurant.DTO.Order.DishInOrderDto;
import pos.restaurant.models.DishInOrder;
import pos.restaurant.service.DishInOrderService;

import java.util.List;

@Controller
@RequestMapping("/api/dishinorder")
public class DishInOrderController {
    private final DishInOrderService dishInOrderService;
    public DishInOrderController(DishInOrderService dishInOrderService) {
        this.dishInOrderService = dishInOrderService;
    }

    @GetMapping("/getAllDishInOrder")
    public ResponseEntity<List<DishInOrderDto>> getAllDishInOrder() {
        return ResponseEntity.ok().body(dishInOrderService.getall()) ;
    }

    @GetMapping("/toggleCooked/{id}")
    public ResponseEntity<DishInOrderDto> toggleCooked(@PathVariable Long id,@RequestParam boolean cooked) {
        return ResponseEntity.ok().body(dishInOrderService.toggleCooked(id, cooked));
    }

    @GetMapping("/toggleServed/{id}")
    public ResponseEntity<DishInOrderDto> toggleServed(@PathVariable Long id,@RequestParam boolean served) {
        return ResponseEntity.ok().body(dishInOrderService.toggleServed(id, served));
    }
}
