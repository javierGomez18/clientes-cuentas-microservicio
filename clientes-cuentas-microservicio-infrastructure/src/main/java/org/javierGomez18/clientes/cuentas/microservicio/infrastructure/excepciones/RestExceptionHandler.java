package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.excepciones;

import org.javierGomez18.clientes.cuentas.microservicio.domain.constants.ErrorMessages;
import org.javierGomez18.clientes.cuentas.microservicio.domain.excepciones.ClienteCuentasNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ClienteCuentasNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleClienteNotFound(ClienteCuentasNotFoundException ex) {
        if (ex.getTipo() == ClienteCuentasNotFoundException.Tipo.BY_TOTAL) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "error", ErrorMessages.CLIENT_NOT_FOUND_ERROR,
                "message", ex.getMessage(),
                "status", HttpStatus.NOT_FOUND.value()
        ));
    }
}
