package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.controller;

import com.javier.infrastructure.auditclient.dto.AuditAction;
import com.javier.infrastructure.auditclient.dto.AuditEntity;
import com.javier.infrastructure.auditclient.dto.AuditMicroservice;
import com.javier.infrastructure.auditclient.feign.AuditFeignClient;
import com.javier.infrastructure.auditclient.mapper.AuditMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.*;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.MovimientoResponseMapper;
import org.javierGomez18.clientes.cuentas.microservicio.web.api.CuentasApi;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.CuentaRQ;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.CuentaUpdateRQ;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.MovimientoRQ;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.MovimientoRS;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CuentasBancariasController implements CuentasApi {

  private final CreateCuentaUseCase createCuentaUseCase;
  private final CreateMovimientoUseCase createMovimientoUseCase;
  private final UpdateCuentaUseCase updateCuentaUseCase;
  private final MovimientoResponseMapper movimientoResponseMapper;
  private final AuditMapper auditMapper;
  private final AuditFeignClient auditFeignClient;

  @Override
  public ResponseEntity<Void> createCuenta(CuentaRQ cuentaRQ) {
    log.info("POST /cuentas - Crear cuenta para cliente: {}", cuentaRQ.getDniCliente());

    var command =
        new CreateCuentaUseCase.CreateCuentaCommand(
            cuentaRQ.getDniCliente(), cuentaRQ.getTipoCuenta().toString(), cuentaRQ.getTotal());

    createCuentaUseCase.createCuenta(command);
    auditFeignClient.createAuditEvent(
        auditMapper.toAuditEventRQ(
            cuentaRQ,
            AuditMicroservice.CLIENTES_CUENTAS.name(),
            AuditAction.CREATE.name(),
            AuditEntity.CUENTA.name(),
            null,
            MDC.get("correlationId")));
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Override
  public ResponseEntity<Void> updateCuenta(Long idCuenta, CuentaUpdateRQ cuentaUpdateRQ) {
    log.info("PUT /cuentas/{} - Actualizar saldo", idCuenta);

    var command = new UpdateCuentaUseCase.UpdateCuentaCommand(idCuenta, cuentaUpdateRQ.getTotal());
    updateCuentaUseCase.updateCuenta(command);
    auditFeignClient.createAuditEvent(
        auditMapper.toAuditEventRQ(
            Map.of(
                "idCuenta", idCuenta,
                "cuentaUpdate", cuentaUpdateRQ),
            AuditMicroservice.CLIENTES_CUENTAS.name(),
            AuditAction.UPDATE.name(),
            AuditEntity.CUENTA.name(),
            idCuenta,
            MDC.get("correlationId")));
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @Override
  public ResponseEntity<MovimientoRS> realizarMovimiento(Long idCuenta, MovimientoRQ movimientoRQ) {
    log.info(
        "POST /cuentas/{}/movimientos - Realizar movimiento de tipo {}",
        idCuenta,
        movimientoRQ.getTipoMovimiento());
    var command =
        new CreateMovimientoUseCase.CreateMovimientoCommand(
            idCuenta,
            movimientoRQ.getIdCuentaDestino(),
            movimientoRQ.getTipoMovimiento().toString(),
            movimientoRQ.getImporte(),
            movimientoRQ.getDescripcion());
    Movimiento movimiento = createMovimientoUseCase.createMovimiento(command);
    auditFeignClient.createAuditEvent(
        auditMapper.toAuditEventRQ(
            Map.of(
                "idCuenta", idCuenta,
                "movimientoRQ", movimientoRQ),
            AuditMicroservice.CLIENTES_CUENTAS.name(),
            AuditAction.CREATE.name(),
            AuditEntity.MOVIMIENTO.name(),
            idCuenta,
            MDC.get("correlationId")));
    return ResponseEntity.ok(movimientoResponseMapper.toResponse(movimiento));
  }
}
