package pos.restaurant.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pos.restaurant.DTO.OrderRequestDto;
import pos.restaurant.DTO.OrderResponseDto;
import pos.restaurant.models.OrderRestaurant;
import pos.restaurant.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getOrdersByWaiter")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByWaiter(HttpServletRequest request) {
        return ResponseEntity.ok(orderService.getAllWaiterOrders(request));
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @GetMapping("/getOrders")
    public ResponseEntity<List<OrderResponseDto>> getOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
