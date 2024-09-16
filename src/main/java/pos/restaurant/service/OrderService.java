package pos.restaurant.service;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pos.restaurant.DTO.EmployeeAccountDto;
import pos.restaurant.DTO.OrderRequestDto;
import pos.restaurant.DTO.OrderResponseDto;
import pos.restaurant.exceptions.EmployeeAccountNotFound;
import pos.restaurant.models.Dish;
import pos.restaurant.models.EmployeeAccount;
import pos.restaurant.models.OrderRestaurant;
import pos.restaurant.models.RestaurantTable;
import pos.restaurant.repository.EmployeeAccountRepository;
import pos.restaurant.repository.OrderRepository;
import pos.restaurant.repository.RestaurantTableRepository;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final EmployeeAccountService employeeAccountService;
    private final EmployeeAccountRepository employeeAccountRepository;
    private final RestaurantTableRepository restaurantTableRepository;

    public OrderService(OrderRepository orderRepository, EmployeeAccountService employeeAccountService, EmployeeAccountRepository employeeAccountRepository, RestaurantTableRepository restaurantTableRepository) {
        this.orderRepository = orderRepository;
        this.employeeAccountService = employeeAccountService;
        this.employeeAccountRepository = employeeAccountRepository;
        this.restaurantTableRepository = restaurantTableRepository;
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

        OrderRestaurant orderRestaurant = new OrderRestaurant();
        orderRestaurant = OrderRequestDto.toEntity(orderRequest, waiter);
        List<Dish> dishes = orderRestaurant.getDish();
        int price = 0;
        for (Dish dish : dishes) {
            price += (int) dish.getPrice();
        }
        orderRestaurant.setPrice(price);
        OrderRestaurant orderRestaurant1 = orderRepository.save(orderRestaurant);
        restaurantTableRepository.save(orderRestaurant1.getRestaurantTable());

        return OrderResponseDto.toDto(orderRestaurant1);
    }


    public List<OrderResponseDto> getAllOrders() {

        List<OrderRestaurant> orderRestaurant = orderRepository.findAll();
        for (OrderRestaurant order : orderRestaurant) {
            System.out.println("is order");
        }
        List<OrderResponseDto> orderResponseDtos =
            orderRestaurant.stream().map(OrderResponseDto::toDto).toList();
        return orderResponseDtos;
    }

}
