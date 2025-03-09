package com.alberto.virtualcave.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.alberto.virtualcave.exception.Error.missingOrInvalidDataInRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception ex){
        ApiError apiError = ApiError.builder()
                .httpStatusCode(HttpStatus.NOT_FOUND.value())
                .message( ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, MissingRequestHeaderException.class})
    public ResponseEntity<Object> handleAll(MissingRequestValueException ex){
        ApiError apiError = ApiError.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .message( ex.getMessage())
                .errors(Arrays.asList(Error.validationError()))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> resourceNotFoundException(MethodArgumentNotValidException ex){
        String errors = ex.getAllErrors().stream().map( err ->
            {
                if(err instanceof FieldError error ){
                    return  String.format("%s %s",error.getField() , error.getDefaultMessage());
                }
                return err.getDefaultMessage();
            }
        ).collect(Collectors.joining("; "));
        ApiError apiError = ApiError.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .message( errors)
                .errors(Arrays.asList(missingOrInvalidDataInRequest()))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError , HttpStatus.BAD_REQUEST);
    }

}
