package com.example.demo;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratarErros {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> erro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> erro() {
        return ResponseEntity.notFound().build();
    }
}
