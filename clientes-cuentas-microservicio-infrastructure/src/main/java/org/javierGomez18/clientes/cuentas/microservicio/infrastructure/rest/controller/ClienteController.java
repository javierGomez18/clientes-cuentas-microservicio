package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.FindCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.ClienteResponseMapper;
import org.javierGomez18.clientes.cuentas.microservicio.web.api.ClientesApi;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.ClienteRS;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClienteController implements ClientesApi {

  private final FindCuentaUseCase findCuentaUseCase;
  private final ClienteResponseMapper clienteResponseMapper;

  @Override
  public ResponseEntity<List<ClienteRS>> findAllClientes() {
    log.info("GET /clientes");
    var clientes = findCuentaUseCase.findAllClientes();
    var responses = clienteResponseMapper.toResponseList(clientes);
    return ResponseEntity.ok(responses);
  }

  @Override
  public ResponseEntity<ClienteRS> findClienteByDni(String dni) {
    log.info("GET /clientes/{}", dni);
    var cliente = findCuentaUseCase.findClienteByDni(dni);
    var response = clienteResponseMapper.toResponse(cliente);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<List<ClienteRS>> findClientesAdultos() {
    log.info("GET /adultos");
    var clientes = findCuentaUseCase.findClientesAdultos();
    var responses = clienteResponseMapper.toResponseList(clientes);
    return ResponseEntity.ok(responses);
  }

  @Override
  public ResponseEntity<List<ClienteRS>> findClientesByTotalGreaterThan(Float cantidad) {
    log.info("GET /total/{}", cantidad);
    var clientes = findCuentaUseCase.findClientesByTotalGreaterThan(cantidad);
    var responses = clienteResponseMapper.toResponseList(clientes);
    return ResponseEntity.ok(responses);
  }
}
