package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageResult;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.FindMovimientoUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.MovimientoResponseMapper;
import org.javierGomez18.clientes.cuentas.microservicio.web.api.MovimientosApi;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.MovimientoPageRS;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.TipoMovimientoEnum;
import org.javierGomez18.infrastructure.auditclient.dto.AuditAction;
import org.javierGomez18.infrastructure.auditclient.dto.AuditEntity;
import org.javierGomez18.infrastructure.auditclient.dto.AuditMicroservice;
import org.javierGomez18.infrastructure.auditclient.feign.AuditFeignClient;
import org.javierGomez18.infrastructure.auditclient.mapper.AuditMapper;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MovimientosController implements MovimientosApi {

  private final FindMovimientoUseCase findMovimientoUseCase;
  private final MovimientoResponseMapper movimientoResponseMapper;
  private final AuditMapper auditMapper;
  private final AuditFeignClient auditFeignClient;

  @Override
  public ResponseEntity<MovimientoPageRS> findMovimientosByCuentaId(
      Long idCuenta, Integer page, Integer size, String sort, TipoMovimientoEnum tipo) {
    log.info("GET /movimientos/{}", idCuenta);

    PageResult<Movimiento> movimientos =
        findMovimientoUseCase.findByCuentaId(idCuenta, page, size, sort);
    MovimientoPageRS response = new MovimientoPageRS();
    response.page(movimientos.page());
    response.size(movimientos.size());
    response.totalElements(movimientos.totalElements());
    response.totalPages(movimientos.totalPages());
    response.content(
        movimientos.content().stream().map(movimientoResponseMapper::toResponse).toList());
    // HashMap y no Map.of: tipo es opcional y Map.of lanza NullPointerException con valores null
    Map<String, Object> auditParams = new HashMap<>();
    auditParams.put("idCuenta", idCuenta);
    auditParams.put("page", page);
    auditParams.put("size", size);
    auditParams.put("sort", sort);
    auditParams.put("tipo", tipo != null ? tipo.name() : null);
    auditFeignClient.createAuditEvent(
        auditMapper.toAuditEventRQ(
            auditParams,
            AuditMicroservice.CLIENTES_CUENTAS.name(),
            AuditAction.CREATE.name(),
            AuditEntity.MOVIMIENTO.name(),
            idCuenta,
            MDC.get("correlationId")));
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
