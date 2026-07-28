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
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageResult;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.FindMovimientoUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.MovimientoResponseMapper;
import org.javierGomez18.clientes.cuentas.microservicio.web.api.MovimientosApi;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.MovimientoPageRS;
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
      Long idCuenta, Integer page, Integer size, String sort, String tipo) {
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
    auditFeignClient.createAuditEvent(
        auditMapper.toAuditEventRQ(
            Map.of(
                "idCuenta", idCuenta,
                "page", page,
                "size", size,
                "sort", sort,
                "tipo", tipo),
            AuditMicroservice.CLIENTES_CUENTAS.name(),
            AuditAction.CREATE.name(),
            AuditEntity.MOVIMIENTO.name(),
            idCuenta,
            MDC.get("correlationId")));
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
