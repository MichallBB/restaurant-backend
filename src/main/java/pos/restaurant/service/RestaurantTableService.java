package pos.restaurant.service;


import org.springframework.stereotype.Service;
import pos.restaurant.DTO.RestaurantTableDto.RestaurantTableDto;
import pos.restaurant.models.RestaurantTable;
import pos.restaurant.repository.RestaurantTableRepository;

import java.util.List;

@Service
public class RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;

    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public RestaurantTableDto createTable(RestaurantTable restaurantTable) {

        RestaurantTable restaurantTable1 = restaurantTableRepository.save(restaurantTable);
        return RestaurantTableDto.toDto(restaurantTable1);
    }

    public List<RestaurantTableDto> getAllTables() {
        List<RestaurantTable> restaurantTables = restaurantTableRepository.findAll();
        return restaurantTables.stream().map(RestaurantTableDto::toDto).toList();
    }


}
