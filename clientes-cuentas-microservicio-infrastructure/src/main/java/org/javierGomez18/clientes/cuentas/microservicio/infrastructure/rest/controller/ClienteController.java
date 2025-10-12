package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javierGomez18.clientes.cuentas.microservicio.find.FindCuentasClientesUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mappers.ToClienteResponseMapper;
import org.javierGomez18.clientes.cuentas.microservicio.web.api.ClientesApi;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.ClienteDTORS;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClienteController implements ClientesApi {

    private final FindCuentasClientesUseCase findCuentasClientesUseCase;
    private final ToClienteResponseMapper toClienteResponseMapper;

    @Override
    public ResponseEntity<ClienteDTORS> getClienteById(String dni) {
        return ResponseEntity.ok(toClienteResponseMapper.toClienteResponse(findCuentasClientesUseCase.getClienteByDni(dni)));
    }

    @Override
    public ResponseEntity<List<ClienteDTORS>> getClientes() {
        return ResponseEntity.ok(toClienteResponseMapper.toClienteResponseLst(findCuentasClientesUseCase.getClientes()));
    }

    @Override
    public ResponseEntity<List<ClienteDTORS>> getClientesAdultos() {
        return ResponseEntity.ok(toClienteResponseMapper.toClienteResponseLst(findCuentasClientesUseCase.getClientesAdultos()));
    }

    @Override
    public ResponseEntity<List<ClienteDTORS>> getClientesCantidadSuperior(Float cantidad) {
        return ResponseEntity.ok(toClienteResponseMapper.toClienteResponseLst(findCuentasClientesUseCase.getClientesByTotal(cantidad)));
    }
}
