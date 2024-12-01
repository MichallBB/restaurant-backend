package pos.restaurant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pos.restaurant.models.history.DishInOrderHistory;
import pos.restaurant.models.history.OrdersHistory;
import pos.restaurant.service.DishHistoryService;
import pos.restaurant.service.DishInOrderService;
import pos.restaurant.service.OrdersHistoryService;

import java.util.List;

@RestController
@RequestMapping("/api/ordersHistory")
public class OrdersHistoryController {
    private final OrdersHistoryService ordersHistoryService;
    private final DishHistoryService dishHistoryService;

    public OrdersHistoryController(OrdersHistoryService ordersHistoryService, DishHistoryService dishHistoryService) {
        this.ordersHistoryService = ordersHistoryService;

        this.dishHistoryService = dishHistoryService;
    }

    @GetMapping("/getAllOrdersHistory")
    public List<OrdersHistory> getAllOrdersHistory() {
        return ordersHistoryService.getAllOrdersHistory();
    }

    @GetMapping("/getToday")
    public List<OrdersHistory> getToday() {
        return ordersHistoryService.getToday();
    }

    @GetMapping("/getDishesFromLastWeek")
    public List<DishInOrderHistory> getDishesFromLastWeek() {
        return dishHistoryService.getAllFromLastWeek();
    }
}
