package by.kirilldikun.onlinestoreapi.exceptions.handler;

import by.kirilldikun.onlinestoreapi.dto.ErrorResponse;
import by.kirilldikun.onlinestoreapi.exceptions.AlreadyExistsException;
import by.kirilldikun.onlinestoreapi.exceptions.EmptyCartException;
import by.kirilldikun.onlinestoreapi.exceptions.IllegalCartItemQuantityException;
import by.kirilldikun.onlinestoreapi.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_FAILED", "The request was not validated");
        e.getBindingResult().getFieldErrors()
                .forEach(fieldError -> errorResponse.addField(fieldError.getField(), fieldError.getDefaultMessage()));
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyExistsException.class)
    public ErrorResponse handleProjectAlreadyExistsException(AlreadyExistsException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("RESOURCE_ALREADY_EXISTS", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("RESOURCE_NOT_FOUND", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalCartItemQuantityException.class)
    public ErrorResponse handleIllegalCartItemQuantityException(IllegalCartItemQuantityException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("ILLEGAL_PARAMETER", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyCartException.class)
    public ErrorResponse handleEmptyCartException(EmptyCartException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("ILLEGAL_STATE", e.getMessage());
    }
}
