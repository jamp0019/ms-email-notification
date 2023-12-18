package com.invexdijin.msemailnotification.application.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class HandlerException {

    @Autowired
    private HashMap<String, Object> responseMap;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            responseMap.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(responseMap);
    }

    @ExceptionHandler({EmailInternalServerError.class})
    public ResponseEntity<Map<String, Object>> internalServerException(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "UPS! Unexpected error, contact system administrator");
        log.error("Unexpected error: ".concat(e.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
