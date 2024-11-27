package pos.restaurant.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pos.restaurant.DTO.Order.OrderRequestDto;
import pos.restaurant.DTO.Order.OrderResponseDto;
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

    @GetMapping("/endOrder/{id}")
    public ResponseEntity<OrderResponseDto> endTheOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.endTheOrder(id));
    }

    @GetMapping("/getAllActiveOrders")
    public ResponseEntity<List<OrderResponseDto>> getAllActiveOrders() {
        return ResponseEntity.ok(orderService.getAllActiveOrders());
    }
}
