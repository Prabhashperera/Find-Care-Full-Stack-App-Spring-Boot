package org.app.findcarespringboot.exception;

import org.app.findcarespringboot.dto.response.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
