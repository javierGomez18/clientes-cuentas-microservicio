package org.javierGomez18.clientes.cuentas.microservicio.application.update;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.UpdateCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.CuentaBancariaCommandRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateCuentaServiceTest {

    @Mock
    private CuentaBancariaCommandRepository cuentaBancariaRepository;

    @InjectMocks
    private UpdateCuentaService service;

    @Test
    void shouldCallRepositoryWithUpdatedCuenta() {
        var cmd = new UpdateCuentaUseCase.UpdateCuentaCommand(1L, 1500.0f);

        service.updateCuenta(cmd);

        ArgumentCaptor<CuentaBancaria> captor = ArgumentCaptor.forClass(CuentaBancaria.class);
        verify(cuentaBancariaRepository).updateCuenta(captor.capture());

        CuentaBancaria updated = captor.getValue();
        assertEquals(1L, updated.getId());
        assertEquals(1500.0f, updated.getTotal());
    }
}
