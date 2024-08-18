package pos.restaurant.exceptions;

public class EmployeeAccountNotFound extends RuntimeException{
    public EmployeeAccountNotFound(String message) {
        super(message);
    }
}
