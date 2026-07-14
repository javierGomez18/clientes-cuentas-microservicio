package org.javierGomez18.clientes.cuentas.microservicio.application.create;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.CreateCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.CuentaBancariaCommandRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateCuentaServiceTest {

    @Mock
    private CuentaBancariaCommandRepository cuentaBancariaRepository;

    @InjectMocks
    private CreateCuentaService service;

    @Test
    void shouldCallRepositoryWithConstructedClienteAndCuenta() {
        var cmd = new CreateCuentaUseCase.CreateCuentaCommand("11111111A", "PREMIUM", 1000.0f);

        service.createCuenta(cmd);

        ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
        ArgumentCaptor<CuentaBancaria> cuentaCaptor = ArgumentCaptor.forClass(CuentaBancaria.class);

        verify(cuentaBancariaRepository, times(1)).addCuenta(clienteCaptor.capture(), cuentaCaptor.capture());

        Cliente capturedCliente = clienteCaptor.getValue();
        CuentaBancaria capturedCuenta = cuentaCaptor.getValue();

        assertEquals("11111111A", capturedCliente.getDni());
        assertNotNull(capturedCliente.getCuentas());
        assertEquals("11111111A", capturedCuenta.getDniCliente());
        assertEquals("PREMIUM", capturedCuenta.getTipoCuenta());
        assertEquals(1000.0f, capturedCuenta.getTotal());
    }
}

