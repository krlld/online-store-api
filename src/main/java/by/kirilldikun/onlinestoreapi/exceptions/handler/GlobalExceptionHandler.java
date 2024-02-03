package by.kirilldikun.onlinestoreapi.exceptions.handler;

import by.kirilldikun.onlinestoreapi.dto.ErrorResponse;
import by.kirilldikun.onlinestoreapi.exceptions.CategoryAlreadyExistsException;
import by.kirilldikun.onlinestoreapi.exceptions.CategoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ErrorResponse handleProjectAlreadyExistsException(CategoryAlreadyExistsException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ErrorResponse handleProjectAlreadyExistsException(CategoryNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
