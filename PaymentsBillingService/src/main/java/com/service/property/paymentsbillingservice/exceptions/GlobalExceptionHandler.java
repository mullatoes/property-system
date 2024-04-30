package com.service.property.paymentsbillingservice.exceptions;

import com.service.property.paymentsbillingservice.utils.UnSuccessfulWrapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<UnSuccessfulWrapper<String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = "An error occurred: " + Objects.requireNonNull(ex.getRootCause()).getMessage();
        UnSuccessfulWrapper<String> response = new UnSuccessfulWrapper<>("failure", errorMessage);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(UnitOccupiedException.class)
    public ResponseEntity<UnSuccessfulWrapper<String>> handleUnitOccupiedException(UnitOccupiedException ex) {
        UnSuccessfulWrapper<String> response = new UnSuccessfulWrapper<>("failure", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(UnitNotFoundException.class)
    public ResponseEntity<UnSuccessfulWrapper<String>> handleUnitNotFoundException(UnitOccupiedException ex) {
        UnSuccessfulWrapper<String> response = new UnSuccessfulWrapper<>("failure", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<UnSuccessfulWrapper<String>> handleCustomNotFoundException(UnitOccupiedException ex) {
        UnSuccessfulWrapper<String> response = new UnSuccessfulWrapper<>("failure", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}


