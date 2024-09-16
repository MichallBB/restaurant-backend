package pos.restaurant.exceptions;

public class DishCategoryNotFound extends RuntimeException{
    public DishCategoryNotFound(String message) {
        super(message);
    }
}