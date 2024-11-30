package pos.restaurant.service;


import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import pos.restaurant.DTO.RestaurantTableDto.RestaurantTableDto;
import pos.restaurant.models.OrderRestaurant;
import pos.restaurant.models.RestaurantTable;
import pos.restaurant.repository.OrderRepository;
import pos.restaurant.repository.RestaurantTableRepository;
import pos.restaurant.DTO.tableAvailable;

import java.util.List;

@Service
public class RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;
    private final OrderRepository orderRepository;

    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository, OrderRepository orderRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.orderRepository = orderRepository;
    }

    public RestaurantTableDto createTable(RestaurantTable restaurantTable) throws IllegalArgumentException {
        String tableNumber = restaurantTable.getTableNumber();
        if (restaurantTableRepository.existsByTableNumber(tableNumber)) {
            throw new IllegalArgumentException("Table number already exists");
        }
        RestaurantTable restaurantTable1 = restaurantTableRepository.save(restaurantTable);
        return RestaurantTableDto.toDto(restaurantTable1);
    }

    public List<RestaurantTableDto> getAllTables() {
        List<RestaurantTable> restaurantTables = restaurantTableRepository.findAll();
        return restaurantTables.stream().map(RestaurantTableDto::toDto).toList();
    }

    public tableAvailable checkTable(String tableNumber) {
        boolean isAvailable = !restaurantTableRepository.existsByTableNumber(tableNumber);
        return new tableAvailable(isAvailable);
    }

    public RestaurantTableDto editTable(String tableNumber, Long id) throws IllegalArgumentException {
        RestaurantTable table = restaurantTableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Table not found"));

        if (restaurantTableRepository.existsByTableNumber(tableNumber)) {
            throw new IllegalArgumentException("Table number already exists");
        }

        table.setTableNumber(tableNumber);
        RestaurantTable restaurantTable = restaurantTableRepository.save(table);
        return RestaurantTableDto.toDto(restaurantTable);
    }

    @Transactional
    public void deleteTable(String tableNumber) throws DataIntegrityViolationException {
        RestaurantTable table = restaurantTableRepository.findByTableNumber(tableNumber)
                .orElseThrow(() -> new IllegalArgumentException("Table not found"));
        boolean isWithRelationWithOrderActive = false;

        List<OrderRestaurant> order = orderRepository.findAllByRestaurantTable(table);
        for (OrderRestaurant orderRestaurant : order) {
            if (!orderRestaurant.isOrderEnded()) {
                isWithRelationWithOrderActive = true;
                break;
            }
        }
        if (isWithRelationWithOrderActive) {
            throw new DataIntegrityViolationException("Cannot delete table with existing orders");
        }
        for (OrderRestaurant orderRestaurant : order) {
            orderRestaurant.setRestaurantTable(null);
            orderRepository.save(orderRestaurant);
        }
        restaurantTableRepository.delete(table);

    }
}
