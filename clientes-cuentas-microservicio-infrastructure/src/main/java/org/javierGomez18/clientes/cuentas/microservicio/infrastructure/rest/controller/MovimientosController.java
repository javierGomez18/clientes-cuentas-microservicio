package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageResult;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.FindMovimientoUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.MovimientoResponseMapper;
import org.javierGomez18.clientes.cuentas.microservicio.web.api.MovimientosApi;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.MovimientoPageRS;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MovimientosController implements MovimientosApi {

    private final FindMovimientoUseCase findMovimientoUseCase;
    private final MovimientoResponseMapper movimientoResponseMapper;

    @Override
    public ResponseEntity<MovimientoPageRS> findMovimientosByCuentaId(Long idCuenta, Integer page, Integer size, String sort, String tipo) {
        log.info("GET /movimientos/{}", idCuenta);


        PageResult<Movimiento> movimientos = findMovimientoUseCase.findByCuentaId(idCuenta, page, size, sort);
        MovimientoPageRS response = new MovimientoPageRS();
        response.page(movimientos.page());
        response.size(movimientos.size());
        response.totalElements(movimientos.totalElements());
        response.totalPages(movimientos.totalPages());
        response.content(movimientos.content().stream().map(movimientoResponseMapper::toResponse).toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
