package org.javierGomez18.clientes.cuentas.microservicio.application.create;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.TipoMovimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.CreateMovimientoUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.CuentaBancariaCommandRepository;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.CuentaBancariaQueryRepository;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.MovimientoCommandRepository;
import org.javierGomez18.clientes.cuentas.microservicio.domain.exception.movimiento.MovimientoUnprocessableEntityException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateMovimientoServiceTest {

    @Mock
    private CuentaBancariaCommandRepository cuentaBancariaCommandRepository;

    @Mock
    private CuentaBancariaQueryRepository cuentaBancariaQueryRepository;

    @Mock
    private MovimientoCommandRepository movimientoCommandRepository;

    @InjectMocks
    private CreateMovimientoService service;

    @Test
    void shouldCreateRetirada_whenSufficientFunds() {
        var cuenta = new CuentaBancaria();
        cuenta.setId(1L);
        cuenta.setTotal(200.0f);

        when(cuentaBancariaQueryRepository.findCuenta(1L)).thenReturn(Optional.of(cuenta));

        var movimientoOrigin = Movimiento.builder()
                .fechaOperacion(LocalDate.now())
                .tipo(TipoMovimiento.RETIRADA)
                .importe(BigDecimal.valueOf(100.0))
                .cuentaId(1L)
                .descripcion("retiro")
                .build();

        when(movimientoCommandRepository.addMovimiento(any())).thenReturn(Optional.of(movimientoOrigin));

        var cmd = new CreateMovimientoUseCase.CreateMovimientoCommand(1L, null, "RETIRADA", 100.0, "retiro");

        var result = service.createMovimiento(cmd);

        assertNotNull(result);
        assertEquals(movimientoOrigin, result);
        assertEquals(100.0f, cuenta.getTotal()); // 200 - 100

        verify(movimientoCommandRepository, times(1)).addMovimiento(any());
        verify(cuentaBancariaCommandRepository, times(1)).updateCuenta(cuenta);
    }

    @Test
    void shouldThrow_whenInsufficientFunds() {
        var cuenta = new CuentaBancaria();
        cuenta.setId(1L);
        cuenta.setTotal(50.0f);

        when(cuentaBancariaQueryRepository.findCuenta(1L)).thenReturn(Optional.of(cuenta));

        var cmd = new CreateMovimientoUseCase.CreateMovimientoCommand(1L, null, "RETIRADA", 100.0, "retiro");

        assertThrows(MovimientoUnprocessableEntityException.class, () -> service.createMovimiento(cmd));

        verify(movimientoCommandRepository, never()).addMovimiento(any());
        verify(cuentaBancariaCommandRepository, never()).updateCuenta(any());
    }

    @Test
    void shouldCreateTransfer_betweenAccounts() {
        var origen = new CuentaBancaria();
        origen.setId(1L);
        origen.setTotal(200.0f);

        var destino = new CuentaBancaria();
        destino.setId(2L);
        destino.setTotal(50.0f);

        when(cuentaBancariaQueryRepository.findCuenta(1L)).thenReturn(Optional.of(origen));
        when(cuentaBancariaQueryRepository.findCuenta(2L)).thenReturn(Optional.of(destino));

        var movOrigin = Movimiento.builder()
                .fechaOperacion(LocalDate.now())
                .tipo(TipoMovimiento.RETIRADA)
                .importe(BigDecimal.valueOf(100.0))
                .cuentaId(1L)
                .descripcion("tran")
                .build();

        var movDest = Movimiento.builder()
                .fechaOperacion(LocalDate.now())
                .tipo(TipoMovimiento.INGRESO)
                .importe(BigDecimal.valueOf(100.0))
                .cuentaId(2L)
                .descripcion("Transferencia recibida: tran")
                .build();

        when(movimientoCommandRepository.addMovimiento(any())).thenReturn(Optional.of(movOrigin), Optional.of(movDest));

        var cmd = new CreateMovimientoUseCase.CreateMovimientoCommand(1L, 2L, "TRANSFERENCIA", 100.0, "tran");

        var result = service.createMovimiento(cmd);

        assertNotNull(result);
        assertEquals(movOrigin, result);
        assertEquals(100.0f, origen.getTotal()); // 200 - 100
        assertEquals(150.0f, destino.getTotal()); // 50 + 100

        verify(movimientoCommandRepository, times(2)).addMovimiento(any());
        verify(cuentaBancariaCommandRepository, times(2)).updateCuenta(any());
    }
}

