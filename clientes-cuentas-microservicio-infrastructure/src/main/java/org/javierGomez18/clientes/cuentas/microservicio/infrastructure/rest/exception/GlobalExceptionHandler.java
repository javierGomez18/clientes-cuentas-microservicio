package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.exception;

import org.javierGomez18.clientes.cuentas.microservicio.domain.exception.ClienteNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Manejador global de excepciones para la capa REST
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleClienteNotFoundException(
            ClienteNotFoundException ex) {
        log.error("ClienteNotFoundException: {}", ex.getMessage());
        
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", "NOT_FOUND",
                "message", ex.getMessage(),
                "tipo", ex.getTipo()
        );

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        log.error("IllegalArgumentException: {}", ex.getMessage());
        
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "BAD_REQUEST",
                "message", ex.getMessage()
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("Excepción inesperada", ex);
        
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "error", "INTERNAL_SERVER_ERROR",
                "message", "Error interno del servidor"
        );

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

