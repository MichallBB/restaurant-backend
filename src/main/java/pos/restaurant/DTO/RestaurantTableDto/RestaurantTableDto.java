package pos.restaurant.DTO.RestaurantTableDto;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pos.restaurant.models.OrderRestaurant;
import pos.restaurant.models.RestaurantTable;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTableDto {
    private Long id;

    private String tableNumber;
    private boolean isOccupied;
    private boolean isReserved;
    private Date reservationTime;


    public static RestaurantTableDto toDto(RestaurantTable restaurantTable) {
        RestaurantTableDto restaurantTableDto = new RestaurantTableDto();
        restaurantTableDto.setId(restaurantTable.getId());
        restaurantTableDto.setTableNumber(restaurantTable.getTableNumber());
        restaurantTableDto.setOccupied(restaurantTable.isOccupied());
        restaurantTableDto.setReserved(restaurantTable.isReserved());
        restaurantTableDto.setReservationTime(restaurantTable.getReservationTime());
        return restaurantTableDto;
    }
}
