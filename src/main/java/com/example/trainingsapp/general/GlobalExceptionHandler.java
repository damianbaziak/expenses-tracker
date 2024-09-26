package com.example.trainingsapp.general;

import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.general.exception.ErrorStrategy;
import com.example.trainingsapp.general.exception.error.ErrorResponseDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    ErrorStrategy errorStrategy;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ExceptionHandler(AppRuntimeException.class)
    public ResponseEntity<String> handleAppRuntimeException(AppRuntimeException exception) {
        return ResponseEntity.status(exception.getHttpStatusCode()).body(exception.getDescription());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(final ConstraintViolationException e) {
        log.error("ConstraintViolationException: {}", e.getMessage());

        return new ResponseEntity<>(
                new ErrorResponseDTO(
                        ErrorCode.TEA003.getBusinessStatus(),
                        errorStrategy.returnExceptionMessage(ErrorCode.TEA003.getBusinessMessage()),
                        errorStrategy.returnExceptionDescription(String.format("Throwable exception %s",
                                e.getMessage())),
                        ErrorCode.TEA003.getHttpStatusCode()),
                HttpStatus.valueOf(ErrorCode.TEA003.getHttpStatusCode())
        );
    }
}
