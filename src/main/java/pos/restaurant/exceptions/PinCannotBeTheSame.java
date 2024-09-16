package pos.restaurant.exceptions;

public class PinCannotBeTheSame extends RuntimeException {
    public PinCannotBeTheSame(String message) {
        super(message);
    }
}
