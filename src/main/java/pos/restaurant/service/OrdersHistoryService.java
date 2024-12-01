package pos.restaurant.service;

import org.springframework.stereotype.Service;
import pos.restaurant.models.history.OrdersHistory;
import pos.restaurant.repository.OrdersHistoryRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrdersHistoryService {
    private final OrdersHistoryRepository ordersHistoryRepository;

    public OrdersHistoryService(OrdersHistoryRepository ordersHistoryRepository) {
        this.ordersHistoryRepository = ordersHistoryRepository;
    }

    public List<OrdersHistory> getAllOrdersHistory() {
        return ordersHistoryRepository.findAll();
    }

    public void createOrderHistory(Date date, String waiterName) {
        OrdersHistory orders = new OrdersHistory();
        orders.setOrderStartTime(date);
        orders.setWaiterName(waiterName);
        ordersHistoryRepository.save(orders);
    }

    public List<OrdersHistory> getToday() {
        List<OrdersHistory> orders = ordersHistoryRepository.findAll();
        List<OrdersHistory> todayOrders = new ArrayList<>();
        for (OrdersHistory order : orders) {
            if (isToday(order)) {
                todayOrders.add(order);
            }
        }
        return todayOrders;
    }

    private boolean isToday(OrdersHistory order) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(order.getOrderStartTime());

        Calendar today = Calendar.getInstance();

        return calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR);
    }
}
