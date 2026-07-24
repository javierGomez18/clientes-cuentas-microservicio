package org.javierGomez18.clientes.cuentas.microservicio.application.find;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageRequest;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageResult;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.MovimientoQueryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindMovimientoServiceTest {

  @Mock private MovimientoQueryRepository movimientoRepository;

  @InjectMocks private FindMovimientoService service;

  @Test
  void findByCuentaId_forwardsToRepository() {
    long cuentaId = 1L;
    var pageResult = new PageResult<Movimiento>(List.of(), 0, 0, 1, 1);
    when(movimientoRepository.findByCuentaId(eq(cuentaId), any(PageRequest.class)))
        .thenReturn(pageResult);

    var result = service.findByCuentaId(cuentaId, 0, 10, "desc");

    assertEquals(pageResult, result);
    verify(movimientoRepository).findByCuentaId(eq(cuentaId), any(PageRequest.class));
  }
}
