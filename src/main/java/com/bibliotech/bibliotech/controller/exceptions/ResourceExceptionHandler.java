package com.bibliotech.bibliotech.controller.exceptions;

import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.HashMap;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);

    }

   @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> handleDataIntegrityViolationException(
        DataIntegrityViolationException e, HttpServletRequest request) {
        String error = "Integrity violation";
        HttpStatus status = HttpStatus.BAD_REQUEST; 
    
        StandardError err = new StandardError(
            Instant.now(),
            status.value(),
            error,
            e.getMessage(), 
            request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }
}
