package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageResult;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.FindMovimientoUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.MovimientoResponseMapper;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.MovimientoRS;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.MovimientoPageRS;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MovimientosControllerUnitTest {

    @Mock
    FindMovimientoUseCase findMovimientoUseCase;

    @Mock
    MovimientoResponseMapper movimientoResponseMapper;

    @InjectMocks
    MovimientosController controller;

    @Test
    void findMovimientosByCuentaId_returnsPage() {
        Movimiento m = new Movimiento();
        m.setId(1L);
        PageResult<Movimiento> pr = PageResult.<Movimiento>builder().content(List.of(m)).page(0).size(1).totalElements(1).totalPages(1).build();
        when(findMovimientoUseCase.findByCuentaId(1L, 0, 10, "desc")).thenReturn(pr);

        MovimientoRS rs = new MovimientoRS();
        rs.setCuentaId(1L);
        when(movimientoResponseMapper.toResponse(m)).thenReturn(rs);

        ResponseEntity<MovimientoPageRS> resp = controller.findMovimientosByCuentaId(1L, 0, 10, "desc", null);
        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(1, resp.getBody().getContent().size());
    }
}
