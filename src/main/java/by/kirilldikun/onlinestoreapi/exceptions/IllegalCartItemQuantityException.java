package by.kirilldikun.onlinestoreapi.exceptions;

public class IllegalCartItemQuantityException extends RuntimeException {

    public IllegalCartItemQuantityException(String message) {
        super(message);
    }
}
