package org.app.findcarespringboot.exception;

import org.app.findcarespringboot.dto.response.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto> handlerGenericException(Exception exception){
        return new ResponseEntity<>(new ApiResponseDto(500, exception.getMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDto> dataAlreadyExistsException(DataAlreadyExistsException e) {
        return new ResponseEntity<>(
                new ApiResponseDto(409, e.getMessage(), null),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiResponseDto> dataNotFoundException(DataNotFoundException e) {
        return new ResponseEntity<>(
                new ApiResponseDto(409 , e.getMessage(), null)
                , HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ApiResponseDto> internalServerErrorException(InternalServerErrorException e) {
        return new ResponseEntity<>(
                new ApiResponseDto(500 , e.getMessage(), null)
                , HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
