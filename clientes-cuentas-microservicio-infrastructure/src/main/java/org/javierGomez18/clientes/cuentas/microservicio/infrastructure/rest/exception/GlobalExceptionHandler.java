package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cliente.ClienteConflictException;
import org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cliente.ClienteNotFoundException;
import org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cliente.ClienteUnprocessableEntityException;
import org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cuenta.CuentaConflictException;
import org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cuenta.CuentaNotFoundException;
import org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cuenta.CuentaUnprocessableEntityException;
import org.javierGomez18.clientes.cuentas.microservicio.domain.exception.movimiento.MovimientoUnprocessableEntityException;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.ProblemDetailRS;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ClienteNotFoundException.class)
  public ResponseEntity<ProblemDetailRS> handleClienteNotFoundException(
      ClienteNotFoundException ex, HttpServletRequest request) {
    log.error("ClienteNotFoundException: {}", ex.getMessage());

    ProblemDetailRS errorResponse = new ProblemDetailRS();
    errorResponse.setTitle("Cliente no encontrado");
    errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
    errorResponse.setTimestamp(LocalDateTime.now().atOffset(java.time.ZoneOffset.UTC));
    errorResponse.setInstance(URI.create(request.getRequestURI()));
    errorResponse.setType(URI.create("/errors/cliente-no-encontrado"));
    errorResponse.setDetail(ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(ClienteConflictException.class)
  public ResponseEntity<ProblemDetailRS> handleClienteConflictException(
      ClienteConflictException ex, HttpServletRequest request) {
    log.error("ClienteConflictException: {}", ex.getMessage());

    ProblemDetailRS errorResponse = new ProblemDetailRS();
    errorResponse.setTitle("Conflicto con cliente");
    errorResponse.setStatus(HttpStatus.CONFLICT.value());
    errorResponse.setTimestamp(LocalDateTime.now().atOffset(java.time.ZoneOffset.UTC));
    errorResponse.setInstance(URI.create(request.getRequestURI()));
    errorResponse.setType(URI.create("/errors/cliente-cuentas-abiertas"));
    errorResponse.setDetail(ex.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(ClienteUnprocessableEntityException.class)
  public ResponseEntity<ProblemDetailRS> handleClienteUnprocessableEntityException(
      ClienteUnprocessableEntityException ex, HttpServletRequest request) {
    log.error("ClienteUnprocessableEntityException: {}", ex.getMessage());

    ProblemDetailRS errorResponse = new ProblemDetailRS();
    errorResponse.setTitle("Cliente no procesable");
    errorResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
    errorResponse.setTimestamp(LocalDateTime.now().atOffset(java.time.ZoneOffset.UTC));
    errorResponse.setInstance(URI.create(request.getRequestURI()));
    errorResponse.setType(URI.create("/errors/cliente-no-valido"));
    errorResponse.setDetail(ex.getMessage());

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
      IllegalArgumentException ex) {
    log.error("IllegalArgumentException: {}", ex.getMessage());

    Map<String, Object> body =
        Map.of(
            "timestamp", LocalDateTime.now(),
            "status", HttpStatus.BAD_REQUEST.value(),
            "error", "BAD_REQUEST",
            "message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CuentaConflictException.class)
  public ResponseEntity<ProblemDetailRS> handleCuentaConflictException(
      CuentaConflictException ex, HttpServletRequest request) {
    log.error("CuentaConflictException", ex);

    ProblemDetailRS errorResponse = new ProblemDetailRS();
    errorResponse.setTitle("Conflicto con cuenta");
    errorResponse.setStatus(HttpStatus.CONFLICT.value());
    errorResponse.setTimestamp(LocalDateTime.now().atOffset(java.time.ZoneOffset.UTC));
    errorResponse.setInstance(URI.create(request.getRequestURI()));
    errorResponse.setType(
        ex.getTipo().equals(CuentaConflictException.Tipo.NO_CERRABLE)
            ? URI.create("/errors/cuenta-no-cerrable")
            : URI.create("/errors/saldo-insuficiente"));
    errorResponse.setDetail(ex.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(CuentaUnprocessableEntityException.class)
  public ResponseEntity<ProblemDetailRS> handleCuentaUnprocessableEntityException(
      CuentaUnprocessableEntityException ex, HttpServletRequest request) {
    log.error("CuentaUnprocessableEntityException", ex);

    ProblemDetailRS errorResponse = new ProblemDetailRS();
    errorResponse.setTitle("Cuenta no procesable");
    errorResponse.setStatus(HttpStatus.CONFLICT.value());
    errorResponse.setTimestamp(LocalDateTime.now().atOffset(java.time.ZoneOffset.UTC));
    errorResponse.setInstance(URI.create(request.getRequestURI()));
    errorResponse.setType(URI.create("/errors/cuenta-junior-mayor-edad"));
    errorResponse.setDetail(ex.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(MovimientoUnprocessableEntityException.class)
  public ResponseEntity<ProblemDetailRS> handleMovimientoUnprocessableEntityException(
      MovimientoUnprocessableEntityException ex, HttpServletRequest request) {
    log.error("MovimientoUnprocessableEntityException", ex);

    ProblemDetailRS errorResponse = new ProblemDetailRS();
    errorResponse.setTitle("Movimiento no procesable");
    errorResponse.setStatus(HttpStatus.CONFLICT.value());
    errorResponse.setTimestamp(LocalDateTime.now().atOffset(java.time.ZoneOffset.UTC));
    errorResponse.setInstance(URI.create(request.getRequestURI()));
    errorResponse.setType(URI.create("/errors/saldo-insuficiente"));
    errorResponse.setDetail(ex.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(CuentaNotFoundException.class)
  public ResponseEntity<ProblemDetailRS> handleCuentaNotFoundException(
      CuentaNotFoundException ex, HttpServletRequest request) {
    log.error("CuentaNotFoundException", ex);

    ProblemDetailRS errorResponse = new ProblemDetailRS();
    errorResponse.setTitle("Cuenta no encontrada");
    errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
    errorResponse.setTimestamp(LocalDateTime.now().atOffset(java.time.ZoneOffset.UTC));
    errorResponse.setInstance(URI.create(request.getRequestURI()));
    errorResponse.setType(URI.create("/errors/cuenta-no-encontrada"));
    errorResponse.setDetail(ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ProblemDetailRS> handleGenericException(
      Exception ex, HttpServletRequest request) {
    log.error("Excepción inesperada", ex);

    ProblemDetailRS errorResponse = new ProblemDetailRS();
    errorResponse.setTitle("Excepción inesperada");
    errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    errorResponse.setTimestamp(LocalDateTime.now().atOffset(java.time.ZoneOffset.UTC));
    errorResponse.setInstance(URI.create(request.getRequestURI()));
    errorResponse.setType(URI.create("/errors/error-interno"));
    errorResponse.setDetail(ex.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
