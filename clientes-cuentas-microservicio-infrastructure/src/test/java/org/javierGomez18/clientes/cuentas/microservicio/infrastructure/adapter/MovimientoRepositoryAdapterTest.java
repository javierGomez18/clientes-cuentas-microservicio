package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.repository.MovimientoJpaRepository;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.MovimientoEntityMapper;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageRequest;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageResult;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MovimientoRepositoryAdapterTest {

    @Mock
    MovimientoJpaRepository movimientoJpaRepository;

    @Mock
    MovimientoEntityMapper movimientoEntityMapper;

    @InjectMocks
    MovimientoRepositoryAdapter adapter;

    @Test
    void findByCuentaId_null_throws() {
        assertThrows(IllegalArgumentException.class, () -> adapter.findByCuentaId(null, PageRequest.builder().page(0).size(10).descending(false).build()));
    }

    @Test
    void addMovimiento_nullCuentaId_throws() throws Exception {
        Movimiento mov = new Movimiento();
        mov.setFechaOperacion(java.time.LocalDate.now());
        mov.setTipo(org.javierGomez18.clientes.cuentas.microservicio.domain.model.TipoMovimiento.INGRESO);
        mov.setImporte(java.math.BigDecimal.ONE);
        // force null cuentaId bypassing Lombok non-null checks
        var field = Movimiento.class.getDeclaredField("cuentaId");
        field.setAccessible(true);
        field.set(mov, null);

        assertThrows(IllegalArgumentException.class, () -> adapter.addMovimiento(mov));
    }

    @Test
    void findByCuentaId_delegatesAndMaps() {
        long cuentaId = 1L;
        @SuppressWarnings("unchecked")
        Page<org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.MovimientoEntity> page = mock(Page.class);
        when(page.getNumber()).thenReturn(0);
        when(page.getSize()).thenReturn(10);
        when(page.getTotalElements()).thenReturn(0L);
        when(page.getTotalPages()).thenReturn(0);
        when(page.getContent()).thenReturn(List.of());

        when(movimientoJpaRepository.findByCuentaId(eq(cuentaId), any(org.springframework.data.domain.PageRequest.class))).thenReturn(page);

        var result = adapter.findByCuentaId(cuentaId, PageRequest.builder().page(0).size(10).descending(false).build());

        assertNotNull(result);
        assertEquals(0, result.page());
    }
}
