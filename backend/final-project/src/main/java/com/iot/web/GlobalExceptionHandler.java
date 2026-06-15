package com.iot.web;

import com.iot.models.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        // En lugar de devolver los detalles exactos de qué campo falló,
        // devolvemos el mensaje unificado que pide el contrato OpenAPI.
        ErrorResponse error = new ErrorResponse(
                "INVALID_REQUEST",
                "Invalid request payload.",
                LocalDateTime.now().toString()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}