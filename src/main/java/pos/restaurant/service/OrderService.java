package pos.restaurant.service;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pos.restaurant.DTO.EmployeeAccountDto;
import pos.restaurant.DTO.Order.OrderRequestDto;
import pos.restaurant.DTO.Order.OrderResponseDto;
import pos.restaurant.exceptions.EmployeeAccountNotFound;
import pos.restaurant.models.Dish;
import pos.restaurant.models.DishInOrder;
import pos.restaurant.models.EmployeeAccount;
import pos.restaurant.models.OrderRestaurant;
import pos.restaurant.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final EmployeeAccountService employeeAccountService;
    private final EmployeeAccountRepository employeeAccountRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final DishInOrderRepository dishInOrderRepository;

    public OrderService(OrderRepository orderRepository, DishRepository dishRepository,
                        EmployeeAccountService employeeAccountService,
                        EmployeeAccountRepository employeeAccountRepository,
                        RestaurantTableRepository restaurantTableRepository,
                        DishInOrderRepository dishInOrderRepository) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
        this.employeeAccountService = employeeAccountService;
        this.employeeAccountRepository = employeeAccountRepository;
        this.restaurantTableRepository = restaurantTableRepository;
        this.dishInOrderRepository = dishInOrderRepository;
    }

    public List<OrderResponseDto> getAllWaiterOrders(HttpServletRequest request) {
        EmployeeAccountDto employeeAccountDto = employeeAccountService.getEmployeeAccountByTokenHeader(request);

        EmployeeAccount employeeAccount = employeeAccountRepository.findById(employeeAccountDto.getId()).orElseThrow(
                () -> new EmployeeAccountNotFound("Employee account not found")
        );

        List<OrderRestaurant> orders = orderRepository.findAllByWaiterId(employeeAccount.getId());

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
        OrderRestaurant orderRestaurant1 = orderRepository.save(orderRestaurant);

        restaurantTableRepository.save(orderRestaurant1.getRestaurantTable());

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

}
