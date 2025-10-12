package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.controller;

import lombok.RequiredArgsConstructor;
import org.javierGomez18.clientes.cuentas.microservicio.create.CreateCuentasClienteUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mappers.ToClienteDomainMapper;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mappers.ToCuentaBancariaDomainMapper;
import org.javierGomez18.clientes.cuentas.microservicio.update.UpdateCuentasClientesUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.web.api.CuentasApi;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.NewCuentaDTORQ;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.UpdateCuentaDTORQ;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CuentasbancariasController implements CuentasApi {

    private final CreateCuentasClienteUseCase createCuentasClienteUseCase;
    private final UpdateCuentasClientesUseCase updateCuentasClientesUseCase;
    private final ToClienteDomainMapper clientDomainMapper;
    private final ToCuentaBancariaDomainMapper cuentaBancariaDomainMapper;

    @Override
    public ResponseEntity<Void> postAddCuenta(NewCuentaDTORQ newCuentaDTORQ) {
        createCuentasClienteUseCase.createCuentaCliente(clientDomainMapper.toClienteDomain(newCuentaDTORQ));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> putUpdateTotalCuenta(Long idCuenta, UpdateCuentaDTORQ updateCuentaDTORQ) {
        updateCuentasClientesUseCase.updateCuenta(cuentaBancariaDomainMapper.toCuentaDomain(idCuenta, updateCuentaDTORQ));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
