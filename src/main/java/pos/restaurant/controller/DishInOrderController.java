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

    private final WebSocketController webSocketController;
    public DishInOrderController(DishInOrderService dishInOrderService, WebSocketController webSocketController) {
        this.dishInOrderService = dishInOrderService;
        this.webSocketController = webSocketController;
    }

    @GetMapping("/getAllDishInOrder")
    public ResponseEntity<List<DishInOrderDto>> getAllDishInOrder() {
        return ResponseEntity.ok().body(dishInOrderService.getall()) ;
    }

    @GetMapping("/toggleCooked/{id}")
    public ResponseEntity<DishInOrderDto> toggleCooked(@PathVariable Long id,@RequestParam boolean cooked) {
        DishInOrderDto dishInOrderDto = dishInOrderService.toggleCooked(id, cooked);
        webSocketController.sendCookeddDishId(id);
        return ResponseEntity.ok().body(dishInOrderDto);
    }

    @GetMapping("/toggleServed/{id}")
    public ResponseEntity<DishInOrderDto> toggleServed(@PathVariable Long id,@RequestParam boolean served) {
        DishInOrderDto dishInOrderDto = dishInOrderService.toggleServed(id, served);
        webSocketController.sendServedDishId(id);
        return ResponseEntity.ok().body(dishInOrderDto);
    }
}
