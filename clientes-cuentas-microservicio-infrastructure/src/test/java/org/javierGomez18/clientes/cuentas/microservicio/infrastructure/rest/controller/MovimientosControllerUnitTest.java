package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageResult;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.FindMovimientoUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.MovimientoResponseMapper;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.MovimientoPageRS;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.MovimientoRS;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.TipoMovimientoEnum;
import org.javierGomez18.infrastructure.auditclient.feign.AuditFeignClient;
import org.javierGomez18.infrastructure.auditclient.mapper.AuditMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class MovimientosControllerUnitTest {

  @Mock FindMovimientoUseCase findMovimientoUseCase;

  @Mock MovimientoResponseMapper movimientoResponseMapper;

  @Mock AuditMapper auditMapper;

  @Mock AuditFeignClient auditFeignClient;

  @InjectMocks MovimientosController controller;

  @Test
  void findMovimientosByCuentaId_returnsPage() {
    Movimiento m = new Movimiento();
    m.setId(1L);
    PageResult<Movimiento> pr =
        PageResult.<Movimiento>builder()
            .content(List.of(m))
            .page(0)
            .size(1)
            .totalElements(1)
            .totalPages(1)
            .build();
    when(findMovimientoUseCase.findByCuentaId(1L, 0, 10, "desc")).thenReturn(pr);

    MovimientoRS rs = new MovimientoRS();
    rs.setCuentaId(1L);
    when(movimientoResponseMapper.toResponse(m)).thenReturn(rs);

    ResponseEntity<MovimientoPageRS> resp =
        controller.findMovimientosByCuentaId(1L, 0, 10, "desc", null);
    assertEquals(200, resp.getStatusCodeValue());
    assertEquals(1, resp.getBody().getContent().size());
  }

  @Test
  void findMovimientosByCuentaId_conTipo_loRegistraEnLaAuditoria() {
    PageResult<Movimiento> vacia =
        PageResult.<Movimiento>builder()
            .content(List.of())
            .page(0)
            .size(10)
            .totalElements(0)
            .totalPages(0)
            .build();
    when(findMovimientoUseCase.findByCuentaId(1L, 0, 10, "desc")).thenReturn(vacia);

    controller.findMovimientosByCuentaId(1L, 0, 10, "desc", TipoMovimientoEnum.INGRESO);

    ArgumentCaptor<Object> params = ArgumentCaptor.forClass(Object.class);
    verify(auditMapper).toAuditEventRQ(params.capture(), any(), any(), any(), eq(1L), any());
    assertEquals("INGRESO", ((Map<?, ?>) params.getValue()).get("tipo"));
  }

  @Test
  void findMovimientosByCuentaId_sinTipo_noFallaYRegistraTipoNulo() {
    PageResult<Movimiento> vacia =
        PageResult.<Movimiento>builder()
            .content(List.of())
            .page(0)
            .size(10)
            .totalElements(0)
            .totalPages(0)
            .build();
    when(findMovimientoUseCase.findByCuentaId(1L, 0, 10, "desc")).thenReturn(vacia);

    // tipo es opcional: antes esto lanzaba NullPointerException por el Map.of del audit
    ResponseEntity<MovimientoPageRS> resp =
        controller.findMovimientosByCuentaId(1L, 0, 10, "desc", null);

    assertEquals(200, resp.getStatusCode().value());
    ArgumentCaptor<Object> params = ArgumentCaptor.forClass(Object.class);
    verify(auditMapper).toAuditEventRQ(params.capture(), any(), any(), any(), eq(1L), any());
    assertNull(((Map<?, ?>) params.getValue()).get("tipo"));
  }
}
