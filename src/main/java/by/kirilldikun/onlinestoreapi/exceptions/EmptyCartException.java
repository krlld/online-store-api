package by.kirilldikun.onlinestoreapi.exceptions;

public class EmptyCartException extends RuntimeException {

    public EmptyCartException(String message) {
        super(message);
    }
}
