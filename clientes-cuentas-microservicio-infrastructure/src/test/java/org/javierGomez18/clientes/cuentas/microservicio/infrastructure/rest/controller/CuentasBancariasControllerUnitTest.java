package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.CreateCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.CreateMovimientoUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.UpdateCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.MovimientoResponseMapper;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.CuentaRQ;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.CuentaUpdateRQ;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.MovimientoRQ;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.MovimientoRS;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CuentasBancariasControllerUnitTest {

  @Mock CreateCuentaUseCase createCuentaUseCase;

  @Mock UpdateCuentaUseCase updateCuentaUseCase;

  @Mock CreateMovimientoUseCase createMovimientoUseCase;

  @Mock MovimientoResponseMapper movimientoResponseMapper;

  @InjectMocks CuentasBancariasController controller;

  @Test
  void createCuenta_callsUseCase_and_returnsCreated() {
    CuentaRQ rq = mock(CuentaRQ.class);
    when(rq.getDniCliente()).thenReturn("11111111A");
    when(rq.getTipoCuenta())
        .thenReturn(
            org.javierGomez18
                .clientes
                .cuentas
                .microservicio
                .web
                .dto
                .CuentaRQ
                .TipoCuentaEnum
                .PREMIUM);
    when(rq.getTotal()).thenReturn(1000.0f);

    ResponseEntity<Void> resp = controller.createCuenta(rq);
    assertEquals(201, resp.getStatusCodeValue());
    verify(createCuentaUseCase).createCuenta(any());
  }

  @Test
  void updateCuenta_callsUseCase_and_returnsOk() {
    CuentaUpdateRQ rq = mock(CuentaUpdateRQ.class);
    when(rq.getTotal()).thenReturn(500.0f);

    ResponseEntity<Void> resp = controller.updateCuenta(1L, rq);
    assertEquals(200, resp.getStatusCodeValue());
    verify(updateCuentaUseCase).updateCuenta(any());
  }

  @Test
  void realizarMovimiento_returnsMappedMovimiento() {
    MovimientoRQ rq = mock(MovimientoRQ.class);
    when(rq.getIdCuentaDestino()).thenReturn(null);
    when(rq.getTipoMovimiento())
        .thenReturn(
            org.javierGomez18
                .clientes
                .cuentas
                .microservicio
                .web
                .dto
                .MovimientoRQ
                .TipoMovimientoEnum
                .RETIRADA);
    when(rq.getImporte()).thenReturn(50.0);
    when(rq.getDescripcion()).thenReturn("desc");

    Movimiento mov = new Movimiento();
    mov.setCuentaId(1L);
    when(createMovimientoUseCase.createMovimiento(any())).thenReturn(mov);

    MovimientoRS rs = new MovimientoRS();
    rs.setCuentaId(1L);
    when(movimientoResponseMapper.toResponse(mov)).thenReturn(rs);

    var resp = controller.realizarMovimiento(1L, rq);
    assertEquals(200, resp.getStatusCodeValue());
    assertEquals(1L, resp.getBody().getCuentaId());
  }
}
