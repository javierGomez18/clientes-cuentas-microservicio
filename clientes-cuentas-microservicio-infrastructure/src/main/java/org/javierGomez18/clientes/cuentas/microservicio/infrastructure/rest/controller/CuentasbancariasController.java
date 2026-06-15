package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.controller;

import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.CreateCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.FindCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.FindMovimientoUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.UpdateCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.CuentaResponseMapper;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.MovimientoResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javierGomez18.clientes.cuentas.microservicio.web.api.CuentasApi;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.CuentaRQ;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.CuentaUpdateRQ;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST: Cuentas Bancarias
 * ⚡ Depende SOLO de puertos IN (casos de uso)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CuentasBancariasController implements CuentasApi {

    private final CreateCuentaUseCase createCuentaUseCase;
    private final FindCuentaUseCase findCuentaUseCase;
    private final UpdateCuentaUseCase updateCuentaUseCase;
    private final CuentaResponseMapper cuentaResponseMapper;

    @Override
    public ResponseEntity<Void> createCuenta(CuentaRQ cuentaRQ) {
        log.info("POST /cuentas - Crear cuenta para cliente: {}", cuentaRQ.getDniCliente());

        var command = new CreateCuentaUseCase.CreateCuentaCommand(
                cuentaRQ.getDniCliente(),
                cuentaRQ.getTipoCuenta().toString(),
                cuentaRQ.getTotal()
        );

        createCuentaUseCase.createCuenta(command);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> updateCuenta(Long idCuenta, CuentaUpdateRQ cuentaUpdateRQ) {
        log.info("PUT /cuentas/{} - Actualizar saldo", idCuenta);

        var command = new UpdateCuentaUseCase.UpdateCuentaCommand(idCuenta, cuentaUpdateRQ.getTotal());
        updateCuentaUseCase.updateCuenta(command);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}


