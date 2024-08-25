package pos.restaurant.Mapper;

import org.mapstruct.Mapper;
import pos.restaurant.DTO.DishCategoryDto;
import pos.restaurant.models.DishCategory;

@Mapper(componentModel = "spring")
public interface DishCategoryMapper {
    DishCategoryDto toDto(DishCategory dishCategory);
}