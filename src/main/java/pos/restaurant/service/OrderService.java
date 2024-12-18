package pos.restaurant.service;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pos.restaurant.DTO.EmployeeAccountDto;
import pos.restaurant.DTO.Order.OrderRequestDto;
import pos.restaurant.DTO.Order.OrderResponseDto;
import pos.restaurant.exceptions.EmployeeAccountNotFound;
import pos.restaurant.models.*;
import pos.restaurant.repository.*;

import java.sql.Time;
import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final EmployeeAccountService employeeAccountService;
    private final EmployeeAccountRepository employeeAccountRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final DishInOrderRepository dishInOrderRepository;
    private final OrdersHistoryService ordersHistoryService;
    private final DishHistoryService dishHistoryService;

    public OrderService(OrderRepository orderRepository, DishRepository dishRepository,
                        EmployeeAccountService employeeAccountService,
                        EmployeeAccountRepository employeeAccountRepository,
                        RestaurantTableRepository restaurantTableRepository,
                        DishInOrderRepository dishInOrderRepository, OrdersHistoryService ordersHistoryService, DishHistoryService dishHistoryService) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
        this.employeeAccountService = employeeAccountService;
        this.employeeAccountRepository = employeeAccountRepository;
        this.restaurantTableRepository = restaurantTableRepository;
        this.dishInOrderRepository = dishInOrderRepository;
        this.ordersHistoryService = ordersHistoryService;
        this.dishHistoryService = dishHistoryService;
    }

    public List<OrderResponseDto> getAllWaiterOrders(HttpServletRequest request) {
        EmployeeAccountDto employeeAccountDto = employeeAccountService.getEmployeeAccountByTokenHeader(request);

        EmployeeAccount employeeAccount = employeeAccountRepository.findById(employeeAccountDto.getId()).orElseThrow(
                () -> new EmployeeAccountNotFound("Employee account not found")
        );

        List<OrderRestaurant> orders = orderRepository.findAllByWaiterIdAndOrderEndedIsFalse(employeeAccount.getId());

        return orders.stream().map(OrderResponseDto::toDto).toList();
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequest) {
        EmployeeAccount waiter = employeeAccountRepository.findById(orderRequest.getWaiterId()).orElseThrow(
                () -> new EmployeeAccountNotFound("Employee account not found")
        );

        OrderRestaurant orderRestaurant = OrderRequestDto.toEntity(orderRequest, waiter);
        orderRestaurant.setId(null);

        List<DishInOrder> dishInOrders = new ArrayList<>();
        dishHistoryService.createDishHistory(orderRestaurant.getDishes());
        for (DishInOrder dishInOrder : orderRestaurant.getDishes()) {
            Dish dish = dishRepository.findById(dishInOrder.getDish().getId()).orElseThrow(
                    () -> new EmployeeAccountNotFound("Dish not found")
            );
            DishInOrder dishInOrder1 = new DishInOrder();
            dishInOrder1.setDish(dish);
            dishInOrder1.setCooked(false);
            dishInOrder1.setServed(false);
            dishInOrder1.setOrderRestaurant(orderRestaurant);
            dishInOrders.add(dishInOrder1);
        }
        dishInOrderRepository.saveAll(dishInOrders);

        List<DishInOrder> dishes = orderRestaurant.getDishes();
        int price = 0;
        for (DishInOrder dishInOrder : dishes) {
            price += (int) dishInOrder.getDish().getPrice();
        }
        orderRestaurant.setPrice(price);

        orderRestaurant.setDishes(dishInOrders);
        orderRestaurant.setOrderEnded(false);
        OrderRestaurant orderRestaurant1 = orderRepository.save(orderRestaurant);

        restaurantTableRepository.save(orderRestaurant1.getRestaurantTable());
        ordersHistoryService.createOrderHistory(new Date(), waiter.getName());
        return OrderResponseDto.toDto(orderRestaurant1);
    }


    public List<OrderResponseDto> getAllOrders() {

        List<OrderRestaurant> orderRestaurant = orderRepository.findAll();
        List<OrderResponseDto> orderResponseDtos =
                orderRestaurant.stream().map(OrderResponseDto::toDto).toList();

        List<OrderResponseDto> sortedOrderResponseDtos = orderResponseDtos.stream()
                .sorted((o1, o2) -> o1.getOrderStartTime().compareTo(o2.getOrderStartTime()))
                .toList();

        return orderResponseDtos;
    }

    public OrderResponseDto endTheOrder(Long id) {
        OrderRestaurant orderRestaurant = orderRepository.findById(id).orElseThrow(
                () -> new EmployeeAccountNotFound("Order not found")
        );

        orderRestaurant.setOrderEnded(true);
        orderRepository.save(orderRestaurant);

        return OrderResponseDto.toDto(orderRestaurant);
    }

    public List<OrderResponseDto> getAllActiveOrders() {
        List<OrderRestaurant> orderRestaurant = orderRepository.findAllByOrderEndedIsFalse();
        List<OrderResponseDto> orderResponseDtos =
                orderRestaurant.stream().map(OrderResponseDto::toDto).toList();

        return orderResponseDtos.stream()
                .sorted(Comparator.comparing(OrderResponseDto::getOrderStartTime))
                .toList();
    }
}
